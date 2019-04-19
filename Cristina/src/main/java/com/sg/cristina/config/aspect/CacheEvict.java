package com.sg.cristina.config.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mark on 16/11/29.
 *
 * @usage 清除过期缓存注解，放置于update delete insert 类型逻辑之上
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheEvict {
    String key();
    String fieldKey() ;
    int expireTime() default 3600;

}
