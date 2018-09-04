package com.eoulu.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.eoulu.util.VerifyCodeUtil;

/**
 * 负责生成验证码的servlet，用户访问该接口可以与自身绑定一个验证码，用于验证
 */
@WebServlet("/Verify")
public class VerifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedImage img = VerifyCodeUtil.paintImage(80, 30);
        String code = VerifyCodeUtil.getCode();

        //把验证码存放到session对象中
        HttpSession session = request.getSession();
        session.removeAttribute("code");
        session.setAttribute("code", code.toLowerCase());

        //生成验证码
        ImageIO.write(img, "jpeg", response.getOutputStream());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
