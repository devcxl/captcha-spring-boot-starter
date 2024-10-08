package cn.devcxl.captcha.properties;

import cn.devcxl.captcha.component.CaptchaProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author devcxl
 */
@ConfigurationProperties(prefix = "captcha")
public class CaptchaProperties {

    /**
     * 开启
     */
    private Boolean enabled = true;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * 验证提供者
     */
    private CaptchaProvider provider = CaptchaProvider.TURNSTILE;

    /**
     * 请求头参数名
     */
    private String headerParameterName = "X-Captcha-Response";

    /**
     * 请求校验地址
     * Google: https://www.google.com/recaptcha/api/siteverify
     * Cloudflare: https://challenges.cloudflare.com/turnstile/v0/siteverify
     */
    private String verifyUrl;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public CaptchaProvider getProvider() {
        return provider;
    }

    public void setProvider(CaptchaProvider provider) {
        this.provider = provider;
    }

    public String getHeaderParameterName() {
        return headerParameterName;
    }

    public void setHeaderParameterName(String headerParameterName) {
        this.headerParameterName = headerParameterName;
    }

    public String getVerifyUrl() {
        return verifyUrl;
    }

    public void setVerifyUrl(String verifyUrl) {
        this.verifyUrl = verifyUrl;
    }
}
