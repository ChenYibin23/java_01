package com.cy.store.mapper;


import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//单元测试必须添加下列的两个注解
//@SpringBootTest:表示标注当前的类是一个测试类，不会随同项目一块打包
//@RunWith:表示启动这个单元测试类（单元测试类是不能运行的），需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {

    //idea有检测功能，接口不能直接创建bean的（动态代理技术来解决）
    @Autowired
    private UserMapper userMapper;

    /*
     * 1、必须使用@Test注解进行修饰
     * 2、返回值类型必须是void
     * 3、方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须是public
     * */
    @Test
    public void insert() {
        User user = new User();
        user.setUsername("tin");
        user.setPassword("132");
        userMapper.insert(user);
    }
    @Test
    public void findByUsername(){
        String username = "tim";
        User usr = userMapper.findByUsername(username);
        System.out.println(usr);
    }
}
