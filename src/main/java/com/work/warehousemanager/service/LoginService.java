package com.work.warehousemanager.service;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

public interface LoginService {
    /**
     * 生成验证码
     */
    BufferedImage captchaImage();
}
