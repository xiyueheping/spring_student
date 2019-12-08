package hdd.dao.daoimpl;

import hdd.dao.Dao_user;
import hdd.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * student表的操作实现类
 */
@Component("daouser") //将当前类注入到容器
public class Dao_userimpl implements Dao_user {
    @Autowired //表示自动注入
    @Qualifier("jdbcTemplate") //将jdbc模板对象注入到当前实例
    private JdbcTemplate template;

    @Override
    public boolean adduser(User s) {
        String sql = "insert into user (username,password) values(?,?)";
        System.out.println("adduser"+template);

        int i = template.update(sql, s.getUsername(),s.getPassword());
        return i>0;
    }

    @Override
    public ArrayList<User> getalluser() {
        String sql3 = "select * from user";
        System.out.println("getalluser"+template);

        List<User> studentList = template.query(sql3, new BeanPropertyRowMapper<User>(User.class));
        return (ArrayList<User>)studentList;
    }

    @Override
    public User getuserbyname(String username) {
        String sql = "select * from user where username=?";
        System.out.println("getuserbyname"+template);
        Map<String, Object> map;
        try{
            map = template.queryForMap(sql, username);
            String username2 = (String) map.get("username");
            String password = (String) map.get("password");
            User s = new User(username2,password);
            return s;
        }catch (Exception e){
            return null;
        }
    }
}
