package cn.devcxl.captcha.component;

import cn.devcxl.captcha.properties.CaptchaProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证码校验器
 *
 * @author devcxl
 */
public class CaptchaVerifier {


    private final CaptchaProperties captchaProperties;

    private final RestTemplate restTemplate;

    public CaptchaVerifier(RestTemplate restTemplate, CaptchaProperties captchaProperties) {
        this.restTemplate = restTemplate;
        this.captchaProperties = captchaProperties;
    }


    public boolean verify(String token) {
        // 构建请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("secret", captchaProperties.getSecretKey());
        requestBody.put("response", token);

        // 发起POST请求
        ResponseEntity<RecaptchaResponse> responseEntity = restTemplate.postForEntity(
                captchaProperties.getVerifyUrl(),
                requestBody,
                RecaptchaResponse.class
        );

        // 检查响应状态并返回结果
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            return responseEntity.getBody().isSuccess();
        }
        return false;
    }

    public boolean verify(String token,double score) {
        // 构建请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("secret", captchaProperties.getSecretKey());
        requestBody.put("response", token);

        // 发起POST请求
        ResponseEntity<RecaptchaResponse> responseEntity = restTemplate.postForEntity(
                captchaProperties.getVerifyUrl(),
                requestBody,
                RecaptchaResponse.class
        );

        // 检查响应状态并返回结果
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            if (responseEntity.getBody().isSuccess()){
                RecaptchaResponse body = responseEntity.getBody();
                double score1 = body.getScore();
                return score1 > score;
            }
        }
        return false;
    }

    private static class RecaptchaResponse {
        private boolean success;
        private double score;
        private String action;
        private String challenge_ts;
        private String hostname;
        private String[] errorCodes;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getChallenge_ts() {
            return challenge_ts;
        }

        public void setChallenge_ts(String challenge_ts) {
            this.challenge_ts = challenge_ts;
        }

        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public String[] getErrorCodes() {
            return errorCodes;
        }

        public void setErrorCodes(String[] errorCodes) {
            this.errorCodes = errorCodes;
        }
    }
}
