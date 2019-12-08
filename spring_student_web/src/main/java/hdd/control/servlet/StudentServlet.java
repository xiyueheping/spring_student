package hdd.control.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import hdd.domain.Student;
import hdd.service.Serv_student;
import hdd.service.Service_user;
import hdd.service.serviceimpl.Serv_studentimpl;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/student/*"})
public class StudentServlet extends BaseServlet {

    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("处理添加student的请求");
        req.setCharacterEncoding("utf-8");
        //响应消息编码方式
        resp.setCharacterEncoding("utf-8");
        //告诉浏览器使用何种方式解码响应数据
        resp.setHeader("content-type","application/json;charset=utf-8");
        //封装响应信息的map对象
        HashMap<String,String> map = new HashMap<>();

        System.out.println("接收到student/add请求");
        String name = req.getParameter("name");
        String sex = req.getParameter("sex");
        int age = Integer.valueOf(req.getParameter("age"));
        String major = req.getParameter("major");
        String city = req.getParameter("city");

        Student s = new Student(name,sex,age,major,city);

        //获取全局域
        ServletContext servletContext = this.getServletContext();
        //从全局域获取spring容器
        ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        System.out.println("app:"+app.hashCode());
        //获取业务层实例
        Serv_student serv_student  = (Serv_student) app.getBean("servicestudent");

        String str = serv_student.addstu(s);
        map.put("msg",str);
        //创建一个jackson的核心对象
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        System.out.println(json);
        resp.getWriter().write(json); //响应登录信息
    }
    public void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("处理删除student的请求");
        req.setCharacterEncoding("utf-8");
        //响应消息编码方式
        resp.setCharacterEncoding("utf-8");
        //告诉浏览器使用何种方式解码响应数据
        resp.setHeader("content-type","application/json;charset=utf-8");
        //封装响应信息的map对象
        HashMap<String,String> map = new HashMap<>();

        System.out.println("接收到student/del 请求");
        int id = Integer.valueOf(req.getParameter("id"));

        //获取全局域
        ServletContext servletContext = this.getServletContext();
        //从全局域获取spring容器
        ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        System.out.println("app:"+app.hashCode());
        //获取业务层实例
        Serv_student serv_student  = (Serv_student) app.getBean("servicestudent");



        String str = serv_student.delstu(id);
        map.put("msg",str);
        //创建一个jackson的核心对象
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        System.out.println(json);
        resp.getWriter().write(json); //响应登录信息
    }
    public void alter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("处理修改student的请求");
        req.setCharacterEncoding("utf-8");
        //响应消息编码方式
        resp.setCharacterEncoding("utf-8");
        //告诉浏览器使用何种方式解码响应数据
        resp.setHeader("content-type","application/json;charset=utf-8");
        //封装响应信息的map对象
        HashMap<String,String> map = new HashMap<>();

        System.out.println("接收到student/alter 请求");
        int id = Integer.valueOf(req.getParameter("id"));
        String name = req.getParameter("name");
        String sex = req.getParameter("sex");
        int age = Integer.valueOf(req.getParameter("age"));
        String major = req.getParameter("major");
        String city = req.getParameter("city");
        Student s = new Student(id,name,sex,age,major,city);
        //获取全局域
        ServletContext servletContext = this.getServletContext();
        //从全局域获取spring容器
        ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        System.out.println("app:"+app.hashCode());
        //获取业务层实例
        Serv_student serv_student  = (Serv_student) app.getBean("servicestudent");
        String str = serv_student.alterstu(s);
        map.put("msg",str);
        //创建一个jackson的核心对象
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        System.out.println(json);
        resp.getWriter().write(json); //响应登录信息
    }
    public void getall(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("处理查询student的请求");
        req.setCharacterEncoding("utf-8");
        //响应消息编码方式
        resp.setCharacterEncoding("utf-8");
        //告诉浏览器使用何种方式解码响应数据
        resp.setHeader("content-type","application/json;charset=utf-8");
        //封装响应信息的map对象
        HashMap<String, ArrayList<Student>> map = new HashMap<>();

        //获取全局域
        ServletContext servletContext = this.getServletContext();
        //从全局域获取spring容器
        ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        System.out.println("app:"+app.hashCode());
        //获取业务层实例
        Serv_student serv_student  = (Serv_student) app.getBean("servicestudent");
        ArrayList<Student> students = serv_student.getall();
        map.put("msg",students);
        //创建一个jackson的核心对象
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        System.out.println(json);
        resp.getWriter().write(json); //响应登录信息
    }
}
