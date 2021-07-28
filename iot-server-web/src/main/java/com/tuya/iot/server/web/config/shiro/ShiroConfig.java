package com.tuya.iot.server.web.config.shiro;

import com.tuya.iot.server.web.i18n.I18nMessage;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/05/28
 */
@Configuration
@Component
public class ShiroConfig {

    /** Shiro生命周期处理器 */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor injectBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE-2)
    public ShiroFilterFactoryBean shiroFilterFactoryBean(ApplicationContext applicationContext
            , I18nMessage i18nMessage) {
        ShiroFilterFactoryBean fac = new ShiroFilterFactoryBean();
        fac.setLoginUrl("/login");
        fac.setSuccessUrl("/");
        fac.setUnauthorizedUrl("/unauthorized.html");
        fac.setSecurityManager(securityManager(applicationContext));
        fac.setFilterChainDefinitionMap(getFilterChainDefinitionMap());
        Map<String,Filter> filterMap = fac.getFilters();

        /**这里有坑，需要自己new，不能使用spring的@Bean创建。
         * 否则shiro的过滤器链逻辑有问题。
         * 否则anon匹配上了某个路径，却还是会和后面的/**匹配
         * */
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setI18nMessage(i18nMessage);
        filterMap.put("authc", loginFilter);

        //PermissionFilter permissionFilter = new PermissionFilter();
        //permissionFilter.setI18nMessage(i18nMessage);
        //filterMap.put("perms", permissionFilter);
        fac.setFilters(filterMap);
        return fac;
    }

    private Map<String, String> getFilterChainDefinitionMap() {
        Map<String,String> map = new LinkedHashMap<>();

        //静态资源
        map.put("/**/*.html", "anon");
        map.put("/**/*.css", "anon");
        map.put("/**/*.js", "anon");
        map.put("/**/*.jpg", "anon");
        map.put("/**/*.png", "anon");
        map.put("/**/*.gif", "anon");

        //文件上传下载路径
        map.put("/files/**", "anon");
        //静态资源都用这个做路径
        map.put("/static/**", "anon");
        //重定向都用这个forward路径
        map.put("/forward/**", "anon");
        map.put("/druid/**", "anon");
        map.put("/mobile/countries", "anon");
        map.put("/hc.do", "anon");
        map.put("/v2/api-docs", "anon");
        map.put("/webjars/**", "anon");
        map.put("/swagger-resources/**", "anon");
        map.put("/configuration/ui", "anon");
        map.put("/configuration/security", "anon");
        //不需要过滤的业务接口
        map.put("/my/password/reset/captcha", "anon");

        //需要过滤的业务接口
        map.put("/", "authc");
        map.put("/my/**", "authc");
        map.put("/**", "authc,perms");
        return map;
    }


    @Bean
    @DependsOn({"tuyaCloudRealm"})
    public DefaultWebSecurityManager securityManager(ApplicationContext applicationContext) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        TuyaCloudRealm realm = applicationContext.getBean(TuyaCloudRealm.class);
        //禁用shiro的认证和权限缓存，到时候在service层用spring-redis的缓存
        realm.setCachingEnabled(false);
        manager.setRealm(realm);
        manager.setSessionManager(sessionManager());
        return manager;
    }

    /**
     * 我们已经用spring整合redis实现了分布式会话，shiro的会话直接交给容器管理。
     * ServletContainerSessionManager 会获取request对象，然后调用其getSession方法获取会话。
     * 这样获取到的会话，实际上就是spring整合redis的分布式会话。
     * */
    @Bean
    public ServletContainerSessionManager sessionManager() {
        ServletContainerSessionManager sessionManager = new ServletContainerSessionManager();
        return sessionManager;
    }

}
