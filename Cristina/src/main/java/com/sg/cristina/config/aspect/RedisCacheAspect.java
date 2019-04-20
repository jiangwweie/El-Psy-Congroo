package com.sg.cristina.config.aspect;//package com.jiangwei.config.aspect;
//

import com.sg.cristina.util.RedisUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * Created by mark on 16/11/29.
 */
@Aspect
@EnableAspectJAutoProxy
@Component
public class RedisCacheAspect {

    private static final Logger logger = Logger.getLogger(RedisCacheAspect.class);
    /**
     * 分隔符
     **/
    private static final String DELIMITER = "|";


    @Autowired
    RedisUtil redisUtil;

    /**
     * Service层切点 使用到了我们定义的 Cacheable 作为切点表达式。
     * 而且我们可以看出此表达式基于 annotation。
     * 并且用于内建属性为查询的方法之上
     */
    @Pointcut("@annotation(com.sg.cristina.config.aspect.Cacheable)")
    public void queryCache() {
    }

    /**
     * Service层切点 使用到了我们定义的 RedisEvict 作为切点表达式。
     * 而且我们可以看出此表达式是基于 annotation 的。
     * 并且用于内建属性为非查询的方法之上，用于更新表
     */
    @Pointcut("@annotation(com.sg.cristina.config.aspect.CacheEvict)")
    public void ClearCache() {
    }


    @Around("queryCache()")
    public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        System.out.println("query cache -------------------------------");
        Method method = getMethod(pjp);
        Cacheable cacheable = method.getAnnotation(Cacheable.class);
        //key的value
        String fieldKey = parseKey(cacheable.fieldKey(), method, pjp.getArgs());
        //使用redis 的hash进行存取，类似于一张表
        result = redisUtil.hget(cacheable.key(), fieldKey);
        if (result == null) {
            System.out.println("cache is empty ,query db--------");
            try {
                result = pjp.proceed();
                System.out.println("get result from db ----------");
                Assert.notNull(fieldKey);
                if (result != null) {
                    redisUtil.hset(cacheable.key(), fieldKey, result);
                    System.out.println("set result to cache ----------");
                }
            } catch (Throwable e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
        return result;
    }

    /*** 定义清除缓存逻辑*/
    @Around(value = "ClearCache()")
    public Object evict(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        System.out.println("clear cache ---------------");
        Method method = getMethod(pjp);
        CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
        String fieldKey = parseKey(cacheEvict.fieldKey(), method, pjp.getArgs());
        //先删除缓存
        redisUtil.hdel(cacheEvict.key(), fieldKey);
        //然后操作db
        result = pjp.proceed();

        return result;
    }

    /**
     * 获取被拦截方法对象
     * <p>
     * MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象
     * 而缓存的注解在实现类的方法上
     * 所以应该使用反射获取当前对象的方法对象
     */
    public Method getMethod(ProceedingJoinPoint pjp) {
        //获取参数的类型
        Object[] args = pjp.getArgs();
        Class[] argTypes = new Class[pjp.getArgs().length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        Method method = null;
        try {

            Object target = pjp.getTarget();
            Class<?> aClass = target.getClass();
            Signature signature = pjp.getSignature();
            method = aClass.getMethod(signature.getName(), argTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (SecurityException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return method;

    }

    /**
     * 获取缓存的key
     * key 定义在注解上，支持SPEL表达式
     *
     * @param
     * @return
     */
    private String parseKey(String key, Method method, Object[] args) {

        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }


}