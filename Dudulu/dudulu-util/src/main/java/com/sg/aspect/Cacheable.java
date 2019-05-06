package com.sg.aspect;


import java.lang.annotation.*;


/**
 * Created by mark on 16/11/29.
 * @usage  缓存注解类
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Cacheable {
        String key();
        String fieldKey() ;
        int expireTime() default 3600;


}
