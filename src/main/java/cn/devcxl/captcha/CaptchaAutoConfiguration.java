package cn.devcxl.captcha;


import cn.devcxl.captcha.component.CaptchaVerifier;
import cn.devcxl.captcha.configuration.CaptchaConfig;
import cn.devcxl.captcha.interceptor.CaptchaInterceptor;
import cn.devcxl.captcha.properties.CaptchaProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

/**
 * @author devcxl
 */
@Configuration
@EnableConfigurationProperties(CaptchaProperties.class)
@ConditionalOnProperty(prefix = "captcha", name = "enabled", havingValue = "true", matchIfMissing = false)
public class CaptchaAutoConfiguration {

    /**
     * 配置拦截器
     *
     * @param captchaInterceptor
     * @return
     */
    @Bean
    @ConditionalOnBean(CaptchaInterceptor.class)
    public CaptchaConfig captchaConfig(CaptchaInterceptor captchaInterceptor) {
        return new CaptchaConfig(captchaInterceptor);
    }

    /**
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    /**
     * 验证器
     *
     * @param restTemplate
     * @param captchaProperties
     * @return
     */
    @Bean
    public CaptchaVerifier captchaVerifier(RestTemplate restTemplate, CaptchaProperties captchaProperties) {
        return new CaptchaVerifier(restTemplate, captchaProperties);
    }


}
