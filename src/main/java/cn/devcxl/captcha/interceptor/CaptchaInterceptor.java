package cn.devcxl.captcha.interceptor;

import cn.devcxl.captcha.annotations.Captcha;
import cn.devcxl.captcha.component.CaptchaVerifier;
import cn.devcxl.captcha.properties.CaptchaProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

/**
 * 验证码拦截器
 *
 * @author devcxl
 */
public abstract class CaptchaInterceptor implements HandlerInterceptor {


    private static final Logger log = LoggerFactory.getLogger(CaptchaInterceptor.class);

    private final CaptchaProperties captchaProperties;

    private final CaptchaVerifier captchaVerifier;


    public CaptchaInterceptor(CaptchaProperties captchaProperties, CaptchaVerifier captchaVerifier) {
        this.captchaProperties = captchaProperties;
        this.captchaVerifier = captchaVerifier;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            Method method = handlerMethod.getMethod();
            Captcha annotation = method.getAnnotation(Captcha.class);
            if (annotation != null) {
                log.debug("Captcha 开启! {} 允许通过的最低评分:{}", request.getRequestURI(), annotation.score());
                boolean checkResult = captchaCheck(request, annotation.score());
                if (!checkResult) {
                    handlerResponse(response);
                }
                return checkResult;
            }
        }
        return true;
    }

    /**
     * 检查
     *
     * @param request
     * @param score
     * @return
     */
    private boolean captchaCheck(HttpServletRequest request, double score) {
        String token = request.getHeader(captchaProperties.getHeaderParameterName());
        log.debug("token:{}", token);
        return captchaVerifier.verify(token);
    }

    /**
     * 处理返回结果
     *
     * @param response
     * @return
     */
    public abstract void handlerResponse(HttpServletResponse response);


}
