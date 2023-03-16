package com.cy.store.controller;


import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 控制层的基类
 */
public class BaseController {
    /*操作成功的状态码*/
    public static final int OK = 200;


    //请求处理方法，这个方法的返回值就是需要传递到前端的数据
    //@ExceptionHandler:
    //表示当出现这个异常时都会統一被拦截到这个方法当中
    //还会自动将异常对象传递到此方法的参数列表上
    //当项目中产生异常，被统一拦截到此方法中，这个方法此时就充当的是请求处理方法，方法的返回值直接给到前端
    @ExceptionHandler(ServiceException.class)
    /*Throwable是Exception的父类，Throwable中包含Exception和ERROR*/
    public JsonResult<Void> handleException(Throwable e) {
        //调用JsonResult中的异常构造器，将异常信息传给message
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicateException) {
            result.setState(4000);  //设置状态码为4000
            result.setMessage("用户名被占用");
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("插入数据时产生未知的异常");
        } else if (e instanceof UserNotfoundException) {
            result.setState(5001);
            result.setMessage("用户不存在的异常");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(5002);
            result.setMessage("用户密码错误的异常");
        }else if(e instanceof UpdateException){
            result.setState(5003);
            result.setMessage("更新数据时产生未知的异常");
        }
        return result;
    }

    /**
     *  获取session对象中的uid
     * @param session
     * @return 当前用户的uid
     */
    protected final Integer getUidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid")
                .toString());
    }

    /**
     * 获取session对象中的username
     * @param session
     * @return 当前用户的username
     */
    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
