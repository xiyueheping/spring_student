package hdd.control.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import hdd.service.Serv_student;
import hdd.service.Service_user;
import hdd.service.serviceimpl.Service_userimpl;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

@WebServlet(urlPatterns = {"/user/*"}) //类名对应一级请求路径，方法名对应二级请求路径
public class UserServlet extends BaseServlet {
    //响应用户登录请求
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("处理user的login请求");
        req.setCharacterEncoding("utf-8");
        //响应消息编码方式
        resp.setCharacterEncoding("utf-8");
        //告诉浏览器使用何种方式解码响应数据
        resp.setHeader("content-type","application/json;charset=utf-8");
        //封装响应信息的map对象
        HashMap<String,String> map = new HashMap<>();

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String checkcodelogin = req.getParameter("checkcodelogin");
        //如果验证码不正确 直接响应登录失败，如果验证码正确再继续验证账号密码
        if(!(checkcodelogin.equalsIgnoreCase((String) req.getSession().getAttribute("checkcodelogin")))){
            req.getSession().removeAttribute("checkcodelogin"); //验证失败之后清除session中验证码信息
            map.put("msg","登录失败,验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(map);
            resp.getWriter().write(json); //响应登录信息
            return;
        }
        req.getSession().removeAttribute("checkcodelogin"); //验证成功之后清除session中验证码信息
        //获取全局域
        ServletContext servletContext = this.getServletContext();
        //从全局域获取spring容器
        ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        System.out.println("app:"+app.hashCode());
        //获取业务层实例
        Service_user serv_student  = (Service_user) app.getBean("serviceuser");
        String s = serv_student.login(username,password);

        //如果登录成功在session中保存用户信息
        if(s.equals("登录成功")){
            req.getSession().setAttribute("username",username);  //登录成功把用户name存入session

        }
        map.put("msg",s);
        //创建一个jackson的核心对象
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        System.out.println(json);
        resp.getWriter().write(json); //响应登录信息
    }
    //响应用户注册请求
    public void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("处理user的register请求");
        //请求与响应数据字符集设置
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        //告诉浏览器使用何种方式解码响应数据
        resp.setHeader("content-type","application/json;charset=utf-8");

        //响应数据的map对象
        HashMap<String,String> map = new HashMap<>();

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String checkcoderegister = req.getParameter("checkcoderegister");
        //如果验证码不正确 直接响应注册失败，如果验证码正确再继续验证账号密码
        if(!(checkcoderegister.equalsIgnoreCase((String) req.getSession().getAttribute("checkcoderegister")))){
            req.getSession().removeAttribute("checkcoderegister"); //验证失败之后清除session中验证码信息
            map.put("msg","注册失败,验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(map);
            resp.getWriter().write(json); //响应登录信息
            return;
        }
        req.getSession().removeAttribute("checkcoderegister"); //验证成功之后清除session中验证码信息

        //获取全局域
        ServletContext servletContext = this.getServletContext();
        //从全局域获取spring容器
        ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        System.out.println("app:"+app.hashCode());
        //获取业务层实例
        Service_user serv_student  = (Service_user) app.getBean("serviceuser");
        //验证码正确再验证账号密码
        String s = serv_student.register(username,password);

        //如果登录成功在session中保存用户信息
        if(s.equals("注册成功")){
            req.getSession().setAttribute("username",username);  //登录成功把用户name存入session

        }

        map.put("msg",s);
        //创建一个jackson的核心对象
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        System.out.println(json);
        resp.getWriter().write(json); //响应登录信息
    }
    public void checkcodelogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("处理登录验证码请求");
        //告诉浏览器使用何种方式解码响应数据
        resp.setHeader("content-type","image/jpeg;charset=utf-8");
        System.out.println("接收到checkcode请求");
        //获取验证码图片
        int width = 100;
        int height = 50;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //填充背景色
        Graphics g = image.getGraphics(); //画笔对象
        g.setColor(Color.orange); //设置画笔颜色
        g.fillRect(0,0,width,height);
        //生成随机字符并绘制到图片上
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String checknum = "";
        Random random = new Random();
        g.setColor(Color.BLUE);
        for(int i=1;i<=4;i++){
            int index = random.nextInt(str.length());
            char ch = str.charAt(index);
            checknum = checknum + ch;
            g.drawString(ch+"",width/5*i,height/2);
        }
        //随机画干扰线
        g.setColor(Color.CYAN);
        for(int i=0;i<5;i++){
            int x1 = random.nextInt(width);
            int x2 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int y2 = random.nextInt(height);
            g.drawLine(x1,x2,y1,y2);
        }
        System.out.println("生成登录验证码为："+checknum);
        req.getSession().setAttribute("checkcodelogin",checknum);
        //获取字节输出流
        ServletOutputStream outputStream = resp.getOutputStream();
        //将图片返回到前端
        ImageIO.write(image,"jpg",outputStream);
    }
    public void checkcoderegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("处理注册验证码请求请求");
        //告诉浏览器使用何种方式解码响应数据
        resp.setHeader("content-type","image/jpeg;charset=utf-8");
        System.out.println("接收到checkcode请求");
        //获取验证码图片
        int width = 100;
        int height = 50;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //填充背景色
        Graphics g = image.getGraphics(); //画笔对象
        g.setColor(Color.orange); //设置画笔颜色
        g.fillRect(0,0,width,height);
        //生成随机字符并绘制到图片上
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String checknum = "";
        Random random = new Random();
        g.setColor(Color.BLUE);
        for(int i=1;i<=4;i++){
            int index = random.nextInt(str.length());
            char ch = str.charAt(index);
            checknum = checknum + ch;
            g.drawString(ch+"",width/5*i,height/2);
        }
        //随机画干扰线
        g.setColor(Color.CYAN);
        for(int i=0;i<5;i++){
            int x1 = random.nextInt(width);
            int x2 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int y2 = random.nextInt(height);
            g.drawLine(x1,x2,y1,y2);
        }
        System.out.println("生成登录验证码为："+checknum);
        req.getSession().setAttribute("checkcoderegister",checknum);
        //获取字节输出流
        ServletOutputStream outputStream = resp.getOutputStream();
        //将图片返回到前端
        ImageIO.write(image,"jpg",outputStream);
    }

}
