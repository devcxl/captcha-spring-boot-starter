package cn.devcxl.captcha.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author devcxl
 */
@Target({ ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Captcha {
    /**
     * 置信度 范围0到1   1-确认为人类  0-确认为机器人
     * @return
     */
    double score() default 0.6;
}
