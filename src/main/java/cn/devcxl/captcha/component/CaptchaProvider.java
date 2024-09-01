package cn.devcxl.captcha.component;


/**
 * @author devcxl
 */

public enum CaptchaProvider {

    /**
     * cloudflare Turnstile
     * <a href="https://developers.cloudflare.com/turnstile">Docs</a>
     */
    TURNSTILE("Turnstile");


    private final String platform;

    CaptchaProvider(String platform) {
        this.platform = platform;
    }
}
