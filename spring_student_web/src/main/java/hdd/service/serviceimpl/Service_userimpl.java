package hdd.service.serviceimpl;

import hdd.dao.Dao_user;
import hdd.dao.daoimpl.Dao_userimpl;
import hdd.domain.User;
import hdd.service.Service_user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 与student表有关的业务操作实现类
 */
@Component("serviceuser")
public class Service_userimpl implements Service_user {
    @Autowired //表示自动注入
    @Qualifier("daouser") //表示将id为userdao的bean实例注入此成员变量
    private Dao_user daoUser;
    @Override
    //登录操作
    public String login(String username,String password) {
        String flag="登录失败，账号不存在";
        //判断账号是否存在
        ArrayList<User> list = daoUser.getalluser();
        for(User s:list){
            //如果存在判断密码是否正确
            if(s.getUsername().equals(username)){
                User u = daoUser.getuserbyname(username);
                if(u.getPassword().equals(password)){
                    flag="登录成功";
                }else{
                    flag="登录失败，密码错误";
                }
            }
        }
        return flag;
    }

    @Override
    //注册操作
    public String register(String username,String password) {
        String flag=null;
        //判断账号是否存在
        ArrayList<User> list = daoUser.getalluser();
        for(User s:list){
            //如果存在说明不能注册
            if(s.getUsername().equals(username)){
                flag = "注册失败，账号已存在";
            }
        }
        //如果账号不存在 进行添加用户操作
        if(flag == null){
            User u = new User(username,password);
            daoUser.adduser(u);
            flag = "注册成功";
        }
        return flag;
    }

    @Override
    //完成查询所有用户业务
    public ArrayList<User> findall(){
        ArrayList<User> list = daoUser.getalluser();
        return list;
    }
}
