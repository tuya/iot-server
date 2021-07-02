package com.tuya.iot.suite.web.config.session;

import com.tuya.iot.suite.core.model.UserToken;
import com.tuya.iot.suite.web.util.SessionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;

import java.util.concurrent.TimeUnit;

/**
 * 主要用来管理session对象
 */
@Slf4j
public class RedisSessionRepository implements SessionRepository<RedisSession> {
    @Value("${spring.session.redis.namespace:iot-suite}")
    private String prefix;

    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 如果不为空，将覆盖默认的超时时间，单位秒
     * {@link ExpiringSession#setMaxInactiveIntervalInSeconds(int)}.
     */
    private Integer defaultMaxInactiveInterval;


    public RedisSessionRepository(RedisTemplate<String, Object> redisTemplate, Integer defaultMaxInactiveInterval) {
        this.redisTemplate = redisTemplate;
        this.defaultMaxInactiveInterval = defaultMaxInactiveInterval;
    }

    @Override
    public RedisSession createSession() {
        RedisSession session = new RedisSession();
        String key = prefix + session.getId();
        log.debug("createSession " + key);
        return session;
    }

    @Override
    public void save(RedisSession session) {
        String key = prefix + session.getId();
        log.debug("save " + key);
        redisTemplate.opsForValue().set(key, session, defaultMaxInactiveInterval, TimeUnit.SECONDS);
        UserToken token = session.getAttribute(SessionContext.USER_TOKEN_ATTR_NAME);
        if(token!=null){
            redisTemplate.opsForValue().set(token.getUserId(), session.getId(), defaultMaxInactiveInterval, TimeUnit.SECONDS);
        }
    }

    @Override
    public RedisSession getSession(String id) {
        RedisSession session = null;
        String key = prefix + id;
        log.debug("getSession " + key);
        RedisSession redisSession = (RedisSession) redisTemplate.opsForValue().get(key);
        if(redisSession!=null){
            UserToken token = redisSession.getAttribute(SessionContext.USER_TOKEN_ATTR_NAME);
            if(token!=null){
                String savedSessionId = (String) redisTemplate.opsForValue().get(token.getUserId());
                if (id.equals(savedSessionId)) {
                    session = redisSession;
                }
            }
        }
        return session;
    }

    @Override
    public void delete(String id) {
        String key = prefix + id;
        log.debug("delete " + key);
        RedisSession session = (RedisSession)redisTemplate.opsForValue().get(key);
        UserToken token = session.getAttribute(SessionContext.USER_TOKEN_ATTR_NAME);
        redisTemplate.delete(key);
        redisTemplate.delete(token.getUserId());
    }

    public void setDefaultMaxInactiveInterval(Integer defaultMaxInactiveInterval) {
        this.defaultMaxInactiveInterval = defaultMaxInactiveInterval;
    }
}
