package com.cy.store.controller;

import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicateException;
import com.cy.store.util.JsonResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于处理前端的请求
 */

/**
 * 请求路径：/users/reg
 * 请求参数：User user
 * 请求类型：POST
 * 响应结果：JsonResult<Void>
 */
//@Controller自动创建该类的实例化对象，并且将该对象交由spring进行管理（用于控制层）
//@Controller
@RestController  //@Controller + @ResponseBody  优点：不需要在类中的每个方法都使用@ResponseBody注解
//表示处理的前端的响应,第一层路径/users
//表示路径localhost:8080/users，即访问该路径时会自动加载该类
@RequestMapping("users")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    //第二层路径/reg
    //同上，访问该路径时会自动运行此方法

    /**
     * 约定大于配置
     * 2.接收数据方式：请求处理方法的参数列表设置为pojo类型接受前端的数据
     * SpringBoot会将前段的url地址中的参数名和pojo类的属性名进行比较，如果
     * 这两个名称相同，则将值注入到pojo类中对应的属性上
     * PS：reg方法的前端表单传回序列化信息username=chen06&password=111111，会自动被注入到pojo对象的对应属性当中
     *
     * @param user
     * @return
     */
    @RequestMapping("reg")
    //@ResponseBody  //表示此方法的响应结果（返回值）以JSON格式进行数据的响应给到前端
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<>(OK);
    }

    /**
     * 2.接收数据方式：请求处理方法的参数列表设置为非pojo类型
     * SpringBoot会直接将请求的参数名和单发的参数名直接进行比较，
     * 名称相同自动注入
     * 服务器自动创建的全局session对象会在访问该方法时自动被注入到其中
     *
     * @param username 用户名
     * @param password 密码
     * @param session
     * @return
     */
    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User user = userService.login(username, password);
        //为session对象注入username，uid
        session.setAttribute("uid", user.getUid());
        session.setAttribute("username", user.getUsername());
        //检查session对象是否正常注入
        System.out.println(getUidFromSession(session));
        System.out.println(getUsernameFromSession(session));
        return new JsonResult<User>(OK, user);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<Void>(OK);
    }
    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        Integer uid = getUidFromSession(session);
        User user = userService.getByUid(uid);
        return new JsonResult<User>(OK,user);
        //此时在前端展示的User仅有phone，email，gender三个属性的值
    }
    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user,HttpSession session){
        //user对象中有四部分的数据：username，phone，email，gender（前端表单传入）
        //需要额外将uid进行注入
        String username = getUsernameFromSession(session);
        Integer uid = getUidFromSession(session);
        userService.changeInfo(uid,username,user);
        return new JsonResult<>(OK);
    }
}
