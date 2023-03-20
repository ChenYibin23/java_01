package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;


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
         * 密码的加密处理：使用md5算法进行实现：md5算法形式：15135daw-fanboy455-fnawkj43554-1654
         * （串 + password + 串）---md5算法进行加密，连续加载三次
         * 盐值 + password + 盐值 ---盐值就是一个随机的字符串
         */
        //原始密码
        String oldPassword = user.getPassword();
        //获取盐值（随机生成一个盐值）
        String salt = UUID.randomUUID().toString().toUpperCase();
        //补全数据，盐值的录入
        user.setSalt(salt);
        //将密码与盐值进行一个加密处理（编写一个方法进行使用）,忽略原有密码的强度，提升了数据的安全性
        String md5Password = getMD5Password(salt, oldPassword);
        //将加密之后的密码写入到数据库中
        user.setPassword(md5Password);


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
        user.setIsDelete(0);


        /**若通过，则执行插入操作*/
        //执行业务注册的功能
        Integer rows = userMapper.insert(user);
        /**若插入过程中产生了数据库的插入异常，则返回结果为0*/
        if (rows != 1) {
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }
        System.out.println("OK");
    }


    /**
     * 定义一个md5算法的加密处理
     */
    private String getMD5Password(String salt, String password) {
        for (int i = 0; i < 3; i++) {
            //md5算法方法的调用（进行连续的三次加密）
            //getBytes()方法返回一个字节流
            //md5DigestAsHex()方法会将一个字节流进行加密并返回一个字符串
            //toUpperCase()方法可以将字符串进行大写转换
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }


    @Override
    public User login(String username, String password) {
        // 调用userMapper的findByUsername()方法，根据参数username查询用户数据
        User result = userMapper.findByUsername(username);
        // 判断查询结果是否为null
        // 是：抛出UserNotFoundException异常
        // 判断查询结果中的isDelete是否为1
        // 是：抛出UserNotFoundException异常
        if (result == null) {
            throw new UserNotfoundException("用戶不存在");
        }
        if (result.getIsDelete() == 1) {
            throw new UserNotfoundException("用戶已注销");
        }

        String oldPassword = result.getPassword();
        String salt = result.getSalt();
        String newMd5Password = getMD5Password(salt, password);
        if (!oldPassword.equals(newMd5Password)) {
            System.out.println("密码错误");
            throw new PasswordNotMatchException("密码错误");
        }
        //创建一个新的对象进行返回，新对象之于就对象并不会有太多的数据，操作效率更高
        User user = new User();
        user.setUsername(result.getUsername());
        user.setUid(result.getUid());
        user.setAvatar(result.getAvatar());

        //将当前用户的用户数据返回，返回的数据是为了辅助其他页面做数据展示使用（uid,username,avatar）
        return user;
    }

    @Override
    public void changePassword(Integer uid,String username, String originalPassword, String newPassword) {
        User user = userMapper.findByUid(uid);
        if (user == null) {
            throw new UserNotfoundException();
        } else if (user.getIsDelete() == 1) {
            throw new UserNotfoundException();
        }

        String userPassword = user.getPassword();
        String salt = user.getSalt();
        String originalMD5Password = getMD5Password(salt, originalPassword);
        if (!(userPassword.equals(originalMD5Password))){
            System.out.println("请输入正确的原密码");
            throw new PasswordNotMatchException();
        }
        String newMD5Password = getMD5Password(salt, newPassword);
        String modifiedName = user.getUsername();
        Date modifiedTime = new Date();
        Integer integer = userMapper.updatePasswordByUid(uid, newMD5Password, modifiedName, modifiedTime);
        if(integer != 1){
            System.out.println("密码修改失败");
            throw new UpdateException();
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if(result == null){
            System.out.println("用户不存在！");
            throw new UserNotfoundException("用户信息不存在");
        } else if (result.getIsDelete() == 1) {
            System.out.println("用户已注销");
            throw new UserNotfoundException();
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

/*在控制层中调用该方法时传入的user对象中仅包含有phone\email\gender，故需要手动将username，uid进行封装到user对象中*/
    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if(result == null){
            System.out.println("用户不存在！");
            throw new UserNotfoundException("用户信息不存在");
        } else if (result.getIsDelete() == 1) {
            System.out.println("用户已注销");
            throw new UserNotfoundException();
        }
        user.setUid(uid);
//        user.setUsername(username);  //在页面中无法进行用户名更改，故用户名保持不变
        Date modifiedTime = new Date();
        String modifiedUser = username;
        user.setModifiedUser(modifiedUser);
        user.setModifiedTime(modifiedTime);
        Integer integer = userMapper.updateInfoByUid(user);
        if(integer != 1){
            System.out.println("用户信息修改失败！");
            throw new UpdateException("更新数据时产生未知异常！");
        }
    }
    @Override
    public void changeAvatar(Integer uid,User user){
        //控制层传入的User对象中仅包含头像三个字段的值
        User result = userMapper.findByUid(uid);
        if(result == null){
            System.out.println("用户不存在！");
            throw new UserNotfoundException("用户信息不存在");
        } else if (result.getIsDelete() == 1) {
            System.out.println("用户已注销");
            throw new UserNotfoundException();
        }
        user.setUid(uid);
        Date modifiedTime = new Date();
        String modifiedUser = result.getUsername();
        user.setModifiedUser(modifiedUser);
        user.setModifiedTime(modifiedTime);
        Integer integer = userMapper.updateAvatarByUid(user);
        if(integer != 1){
            System.out.println("头像信息修改失败！");
            throw new UpdateException("更新数据时产生未知异常！");
        }

    }

}
