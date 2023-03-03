package com.cy.store.entity;

/**
 *     uid INT AUTO_INCREMENT COMMENT '用户id',
 *     username VARCHAR(20) NOT NULL UNIQUE COMMENT '用户名',
 *     password CHAR(32) NOT NULL COMMENT '密码',
 *     salt CHAR(36) COMMENT '盐值',
 *     phone VARCHAR(20) COMMENT '电话号码',
 *     email VARCHAR(30) COMMENT '电子邮箱',
 *     gender INT COMMENT '性别:0-女，1-男',
 *     avatar VARCHAR(50) COMMENT '头像',
 *     is_delete INT COMMENT '是否删除：0-未删除，1-已删除',
 */

import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体类：使用@Component进行实体类的初始化
 * SpringBoot：约定大于配置，无需使用@Component注解，自动进行初始化
 */
@Data
public class User extends BaseEntity implements Serializable {
    private Integer uid;
    private String username;
    private String password;
    private String salt;
    private String phone;
    private String email;
    private Integer gender;
    private String avatar;
    private Integer isDelete;
}
