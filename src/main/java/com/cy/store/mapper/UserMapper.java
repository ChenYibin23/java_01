package com.cy.store.mapper;

import com.cy.store.entity.User;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 用户模块的持久层接口
 */

/**
 * 添加Mapper注解之后在编译时会自动生成一个实现类
 * 此处不使用Mapper，而是在启动类Store中直接使用@ScanMapper自动进行包的扫描配置mapper，防止需要进行过多的mapper注解
 */
//@Mapper
public interface UserMapper {
    /**
     * 插入用户的数据
     *
     * @param user 用户的数据
     * @return 受影响的行数（增删改都受影响的行数作为返回值，可以根据返回值来判断是否执行成功）
     */
    Integer insert(User user);

    /**
     * 根据用户名来查询用户的数据
     *
     * @param username 用户名
     * @return 如果找到对应的用户则返回这个用户的数据，没有找到则返回null值
     */
    User findByUsername(String username);

    /**
     * 根据uid更新用户的密码
     *
     * @param uid          用户的id
     * @param password     新密码
     * @param modifiedUser 最后修改执行人
     * @param modifiedTime 最后修改时间
     * @return 受影响的行数
     */
    Integer updatePasswordByUid(
            /**
             * 用注解来简化xml配置时，@Param注解的作用是给参数命名，参数命名后就能根据名字得到参数
             * 值，正确的将参数传入sql语句中。@Param("参数名")注解中的参数名需要和sql语句中的#{参数
             * 名}的参数名保持一致。
             */
            @Param("uid") Integer uid,
            @Param("password") String password,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据用户id查询用户数据
     *
     * @param uid 用户ID
     * @return 匹配的用户数量
     */
    User findByUid(Integer uid);

    /**
     * 根据用户id修改用户的资料
     *
     * @param user
     * @return 修改的行数
     */
    Integer updateInfoByUid(
            User user
    );

    /**
     * 根据用户的id来修改用户的头像
     * @param user 用户
     * @return 影响的行数
     */
    Integer updateAvatarByUid(User user);
}
