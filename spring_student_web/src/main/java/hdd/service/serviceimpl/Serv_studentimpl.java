package hdd.service.serviceimpl;


import hdd.dao.Dao_student;
import hdd.dao.daoimpl.Dao_studentimpl;
import hdd.domain.Student;
import hdd.service.Serv_student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component("servicestudent")
public class Serv_studentimpl implements Serv_student {

    @Autowired //表示自动注入
    @Qualifier("daostudent") //表示将id为userdao的bean实例注入此成员变量
    private Dao_student dao_student;
    @Override
    //添加一个学生信息
    public String addstu(Student s) {
        String flag;
        String name = s.getName();
        Student student = dao_student.getstudentbyname(name);
        if(student==null){
            boolean b = dao_student.addstudent(s);
            if(b){
                flag = "添加成功";
            }else {
                flag = "添加失败";
            }
        }else {
            flag = "添加失败，姓名已存在";
        }
        return flag;
    }

    @Override
    public String delstu(int id) {
        boolean b = dao_student.delstudent(id);
        if (b==true){
            return "删除成功";
        }else {
            return "删除失败";
        }
    }

    @Override
    public String alterstu(Student s) {
        boolean b = dao_student.alterstudent(s);
        if (b==true){
            return "修改成功";
        }else {
            return "修改失败";
        }
    }

    @Override
    //获取所有学生
    public ArrayList<Student> getall() {
        ArrayList<Student> students = dao_student.getallstudent();
        return students;
    }

    @Override
    //根据name获取一个学生
    public Student getstubyname(String name) {
        Student s = dao_student.getstudentbyname(name);
        return s;
    }
}
