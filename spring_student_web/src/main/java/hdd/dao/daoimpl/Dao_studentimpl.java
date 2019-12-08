package hdd.dao.daoimpl;

import hdd.dao.Dao_student;
import hdd.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component("daostudent") //将当前类注入到容器
public class Dao_studentimpl implements Dao_student {
    @Autowired //表示自动注入
    @Qualifier("jdbcTemplate") //将jdbc模板对象注入到当前实例
    private JdbcTemplate template;

    @Override
    //新增一个学生
    public boolean addstudent(Student s) {
        String sql = "insert into student (name,sex,age,major,city) values(?,?,?,?,?)";
        int i = template.update(sql, s.getName(), s.getSex(),s.getAge(),s.getMajor(),s.getCity());
        return i>0;
    }

    @Override
    //获取所有学生
    public ArrayList<Student> getallstudent() {
        String sql3 = "select * from student";
        List<Student> studentList = template.query(sql3, new BeanPropertyRowMapper<Student>(Student.class));
        return (ArrayList<Student>)studentList;
    }

    @Override
    //根据name获取一个学生
    public Student getstudentbyname(String sname) {
        String sql = "select * from student where name=?";
        Map<String, Object> map;
        try{
            map = template.queryForMap(sql, sname);
            long id = (Long) map.get("id");
            String name = (String) map.get("name");
            String sex = (String) map.get("sex");
            long age = (Integer) map.get("age");
            String major = (String) map.get("major");
            String city = (String) map.get("city");
            Student s = new Student(id,name,sex,age,major,city);
            return s;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    //根据id删除一个学生
    public boolean delstudent(int id) {
        String sql = "delete from student where id = ?";
        int i = template.update(sql, id);
        return i>0;
    }

    @Override
    //根据id修改一个学生
    public boolean alterstudent(Student s) {
        String sql = "update student set name=?,sex=?,age=?,major=?,city=? where id=?";
        int i = template.update(sql,s.getName(),s.getSex(),s.getAge(),s.getMajor(),s.getCity(),s.getId());
        return i>0;
    }

    public static void main(String[] args) {
        new Dao_studentimpl().getallstudent();
    }
}
