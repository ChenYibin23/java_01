package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Mapper;

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
     *  插入用户的数据
     * @param user 用户的数据
     * @return 受影响的行数（增删改都受影响的行数作为返回值，可以根据返回值来判断是否执行成功）
     */
    Integer insert(User user);

    /**
     * 根据用户名来查询用户的数据
     * @param username 用户名
     * @return 如果找到对应的用户则返回这个用户的数据，没有找到则返回null值
     */
    User findByUsername(String username);
}
