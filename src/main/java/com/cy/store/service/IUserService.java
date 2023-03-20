package com.cy.store.service;

import com.cy.store.entity.User;

public interface IUserService {
    /**
     * 执行用户注册的操作
     * @param user 注册的用户
     */
    void reg(User user);
    /**
     * 用户进行登录的方法
     * @param username 用户名
     * @param password 密码
     * @return 用户登录成功之后返回的对象
     */
    User login(String username , String password);

    /**
     * 修改密码
     * @param uid
     */


    void changePassword(Integer uid,String username, String originalPassword, String newPassword);

    /**
     * 根据用户的uid查询用户的信息
     *
     * @param uid
     * @return
     */
    User getByUid(Integer uid);

    /**
     * 在session中可以获取到uid和username，在控制层将uid和username注入到user对象中
     * 用户信息的更新操作
     * @param uid 用户的id
     * @param username 用户的名称
     * @param user 用户
     */
    void changeInfo(Integer uid,String username,User user);

    /**
     * uid从session中获取
     * @param uid
     * @param user
     */
    void changeAvatar(Integer uid,User user);
}
