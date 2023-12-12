package com.work.warehousemanager.controller;

import cn.hutool.core.util.StrUtil;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.RedisConstants;
import com.work.warehousemanager.constants.WarehouseConstants;
import com.work.warehousemanager.dto.user.LoginUser;
import com.work.warehousemanager.service.AuthInfoService;
import com.work.warehousemanager.service.LoginService;
import com.work.warehousemanager.service.UserInfoService;
import io.netty.util.internal.StringUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class LoginController {

    @Resource
    private LoginService loginService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private AuthInfoService authInfoService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/logout")
    public Result logout(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        stringRedisTemplate.delete(RedisConstants.LOGIN_TOKEN_KEY + token);
        return Result.ok("提出系统！");
    }

    @RequestMapping("/user/auth-list")
    public Result authList(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        return authInfoService.queryAuthList(token);
    }

    @RequestMapping("/curr-user")
    public Result currUser(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        return userInfoService.currUser(token);
    }

    @RequestMapping("/login")
    public Result login(@RequestBody LoginUser loginUser) {
        // 获取前端参数并验证非空
        String userCode = loginUser.getUserCode();
        String userPwd = loginUser.getUserPwd();
        String code = loginUser.getVerificationCode();
        if (StrUtil.isEmpty(userCode) || StrUtil.isEmpty(userPwd) || StrUtil.isEmpty(code)) {
            return Result.err(Result.BAD_REQUEST, "请求参数错误！");
        }
        Result result = userInfoService.loginUser(loginUser);
        return result;
    }

    @RequestMapping("/captcha/captchaImage")
    public void captchaImage(HttpServletResponse response) {

        ServletOutputStream out = null;
        try {
            // 设置验证码，并保存到 redis
            BufferedImage image = loginService.captchaImage();
            // 设置响应对象类型
            response.setContentType("image/jpeg");
            // 获取响应流
            out = response.getOutputStream();
            // 将图片响应到前端
            ImageIO.write(image, "jpg", out);
            out.flush();
            System.out.println("验证码生成！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
