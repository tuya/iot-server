package com.tuya.iot.suite.web.config.shiro;

import com.tuya.iot.suite.web.i18n.I18nMessage;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;
/**
 * @description:
 * @author: benguan.zhou@tuya.com
 * @date: 2021/05/28
 */
@Configuration
public class ShiroConfig {

    //Shiro生命周期处理器
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor injectBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    //开启注解权限验证
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    //开启注解权限验证
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(RedisTemplate redisTemplate) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager(redisTemplate));
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public Realm realm() {
        TuyaCloudRealm customRealm = new TuyaCloudRealm();
        return customRealm;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/", "authc");
        //文件上传下载路径
        chainDefinition.addPathDefinition("/files/**", "anon");
        //静态资源都用这个做路径
        chainDefinition.addPathDefinition("/static/**", "anon");
        //重定向都用这个forward路径
        chainDefinition.addPathDefinition("/forward/**", "anon");
        chainDefinition.addPathDefinition("/druid/**", "anon");
        chainDefinition.addPathDefinition("/**", "authc,perms");
        return chainDefinition;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(RedisTemplate redisTemplate
            , I18nMessage i18nMessage) {
        ShiroFilterFactoryBean fac = new ShiroFilterFactoryBean();
        fac.setLoginUrl("/login");
        fac.setSuccessUrl("/");
        fac.setUnauthorizedUrl("/unauthorized.html");
        fac.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
        fac.setSecurityManager(securityManager(redisTemplate));
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("authc", loginFilter(i18nMessage));
        //filterMap.put("perms", permissionFilter);
        fac.setFilters(filterMap);
        return fac;
    }

    @Bean
    public LoginFilter loginFilter(I18nMessage i18nMessage) {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setI18nMessage(i18nMessage);
        return loginFilter;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(RedisTemplate redisTemplate) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm());
        manager.setCacheManager(cacheManager(redisTemplate));
        manager.setSessionManager(sessionManager());
        return manager;
    }

    @Bean
    public ShiroRedisCacheManager cacheManager(RedisTemplate redisTemplate) {
        ShiroRedisCacheManager cacheManager = new ShiroRedisCacheManager();
        Map<String, ShiroRedisCache> cacheMap = new HashMap<>();
        cacheMap.put("authenCache", new ShiroRedisCache("authenCache", redisTemplate));
        cacheManager.setCacheMap(cacheMap);
        return cacheManager;
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
