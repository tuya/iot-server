package com.tuya.iot.suite.web.config.shiro;

import com.tuya.iot.suite.web.i18n.I18nMessage;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
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

    //Shiro生命周期处理器
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor injectBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(ApplicationContext applicationContext
            , I18nMessage i18nMessage) {
        ShiroFilterFactoryBean fac = new ShiroFilterFactoryBean();
        fac.setLoginUrl("/login");
        fac.setSuccessUrl("/");
        fac.setUnauthorizedUrl("/unauthorized.html");
        fac.setSecurityManager(securityManager(applicationContext));
        fac.setFilterChainDefinitionMap(getFilterChainDefinitionMap());
        Map<String,Filter> filterMap = fac.getFilters();
        filterMap.put("authc", loginFilter(i18nMessage));
        //filterMap.put("perms", permissionFilter(applicationContext));
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
    public LoginFilter loginFilter(I18nMessage i18nMessage) {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setI18nMessage(i18nMessage);
        return loginFilter;
    }

    @Bean
    @DependsOn({"i18nMessage"})
    public PermissionFilter permissionFilter(ApplicationContext applicationContext){
        I18nMessage i18nMessage = applicationContext.getBean(I18nMessage.class);
        PermissionFilter filter = new PermissionFilter();
        filter.setI18nMessage(i18nMessage);
        return filter;
    }

    @Bean
    @DependsOn({"tuyaCloudRealm"})
    public DefaultWebSecurityManager securityManager(ApplicationContext applicationContext) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        TuyaCloudRealm realm = applicationContext.getBean(TuyaCloudRealm.class);
        //禁用shiro的认证和权限缓存，到时候在service层用spring-redis的缓存
        realm.setCachingEnabled(false);
        //realm.setAuthorizationCacheName("authorCache");
        //realm.setAuthenticationCacheName("authenCache");
        manager.setRealm(realm);
        //manager.setCacheManager(cacheManager(applicationContext));
        manager.setSessionManager(sessionManager());
        return manager;
    }

    /*@Bean
    @DependsOn({"redisTemplate"})
    public ShiroRedisCacheManager cacheManager(ApplicationContext applicationContext) {
        RedisTemplate redisTemplate = applicationContext.getBean("redisTemplate",RedisTemplate.class);
        ShiroRedisCacheManager cacheManager = new ShiroRedisCacheManager();
        Map<String, ShiroRedisCache> cacheMap = new HashMap<>();
        cacheMap.put("authenCache", new ShiroRedisCache("authenCache", redisTemplate));
        cacheMap.put("authorCache", new ShiroRedisCache("authorCache", redisTemplate));
        cacheManager.setCacheMap(cacheMap);
        return cacheManager;
    }*/

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
