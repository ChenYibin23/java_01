package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;





@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        /**首先确定当前用户名是否存在*/
        String username = user.getUsername();
        User result = userMapper.findByUsername(username);
        /**如果返回的结果为null，则说明当前用户名未被占用，如果不为null，则抛出UsernameDuplicateException异常*/
        if (result != null) {
            throw new UsernameDuplicateException("当前用户名被占用");
        }

        /**
         * 在进行插入之前，还需要给用户的一些默认属性进行补全
         * created_user VARCHAR(20) COMMENT '日志-创建人',
         * created_time DATETIME COMMENT '日志-创建时间',
         * modified_user VARCHAR(20) COMMENT '日志-最后修改执行人',
         * modified_time DATETIME COMMENT '日志-最后修改时间',
         * is_delete INT COMMENT '是否删除：0-未删除，1-已删除',
         */
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);


        /**若通过，则执行插入操作*/
        //执行业务注册的功能
        Integer rows = userMapper.insert(user);
        /**若插入过程中产生了数据库的插入异常，则返回结果为0*/
        if (rows != 1) {
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }
        System.out.println("OK");
    }
}
