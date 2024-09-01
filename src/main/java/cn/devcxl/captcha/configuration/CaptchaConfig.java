package cn.devcxl.captcha.configuration;

import cn.devcxl.captcha.interceptor.CaptchaInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 *
 * @author devcxl
 */
public class CaptchaConfig implements WebMvcConfigurer {
    private final CaptchaInterceptor captchaInterceptor;

    public CaptchaConfig(CaptchaInterceptor captchaInterceptor) {
        this.captchaInterceptor = captchaInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(captchaInterceptor);
    }
}
