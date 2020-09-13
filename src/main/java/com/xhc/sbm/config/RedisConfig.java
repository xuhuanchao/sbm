package com.xhc.sbm.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static com.alibaba.fastjson.serializer.SerializerFeature.*;


@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(
//                PrettyFormat,
//                WriteNullListAsEmpty,
//                WriteNullStringAsEmpty,
//                WriteDateUseDateFormat,
//                WriteMapNullValue);
//        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        FastJsonRedisSerializer<Object> jsonSerializer = new FastJsonRedisSerializer<>(Object.class);
//        jsonSerializer.setFastJsonConfig(fastJsonConfig);


        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setEnableDefaultSerializer(false);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jsonSerializer);

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jsonSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;

    }

}
