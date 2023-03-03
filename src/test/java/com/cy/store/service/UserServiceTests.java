package com.cy.store.service;


import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.ServiceException;
import com.cy.store.service.ex.UsernameDuplicateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Provider;

//单元测试必须添加下列的两个注解
//@SpringBootTest:表示标注当前的类是一个测试类，不会随同项目一块打包
//@RunWith:表示启动这个单元测试类（单元测试类是不能运行的），需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {


    @Autowired
    private IUserService userService;

    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("chen");
            user.setPassword("123321");
            userService.reg(user);
        }catch (ServiceException e){
            System.out.println("创建失败");
            //获取异常类的名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常的信息
            System.out.println(e.getMessage());
        }
    }
}
