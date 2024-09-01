# captcha-spring-boot-stater

## 使用方式

1. 引入pom

```xml

<dependency>
    <groupId>cn.devcxl.captcha</groupId>
    <artifactId>captcha-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

2. 添加`properties`配置

```properties
captcha.enabled=true
captcha.provider=turnstile
captcha.secret-key=0x4AAAAAAxxxxxxxxxxxxxxxxxxx8
captcha.header-parameter-name=x-captcha-response
captcha.verify-url=https://challenges.cloudflare.com/turnstile/v0/siteverify
```

3. 处理验证失败的情况

```java

@Component
public class CaptchaConfig extends CaptchaInterceptor {

    public Config(CaptchaProperties captchaProperties, CaptchaVerifier captchaVerifier) {
        super(captchaProperties, captchaVerifier);
    }

    @Override
    public void handlerResponse(HttpServletResponse response) {
        try {
            PrintWriter writer = response.getWriter();
            writer.println("Verify Failed!");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
```

4. 前端添加turnstile显式渲染组件

```html
    <!-- 引用CF的验证码js 显示渲染-->
    <script src="https://challenges.cloudflare.com/turnstile/v0/api.js?onload=onloadTurnstileCallback" defer></script>
```
5. 添加js完成

```javascript
    window.onloadTurnstileCallback = function () {
        turnstile.render('#example-container', {
            sitekey: 'xxxxxxxxxxxxxx',
            callback: function (token) {
                console.log(`Challenge Success ${token}`);
                localStorage.setItem("token", token);
            },
        });
    };
```
6. 前端完整代码

```html
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引用CF的验证码js 显示渲染-->
    <script src="https://challenges.cloudflare.com/turnstile/v0/api.js?onload=onloadTurnstileCallback" defer></script>
    <title>Document</title>
</head>

<body>
<div id="example-container"></div>

<button id="button" type="button">Log in</button>

<script>
    window.onloadTurnstileCallback = function () {
        turnstile.render('#example-container', {
            sitekey: 'xxxxxxxxxxxxxx',
            callback: function (token) {
                console.log(`Challenge Success ${token}`);
                localStorage.setItem("token", token);
            },
        });
    };

    document.getElementById('button').addEventListener('click', function () {
        const captchaToken = localStorage.getItem("token");

        if (!captchaToken) {
            console.error('Captcha token not found. Please complete the captcha challenge.');
            return;
        }

        fetch('/test', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'x-captcha-response': captchaToken
            },
            body: JSON.stringify({})
        })
                .then(response => response.json())
                .then(data => console.log(data))
                .catch(error => console.error('Error:', error));
    });
</script>
</body>

</html>

```