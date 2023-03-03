package com.cy.store;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;


@SpringBootTest
class StoreApplicationTests {
    @Autowired  //进行自动注入
    private DataSource dataSource;

    @Test
    void contextLoads() {
    }

//    DBCP
//    C3P0
//    Hikari:底层采用C3P0管理数据库连接对象
//    HikariProxyConnection@2009218448 wrapping com.mysql.cj.jdbc.ConnectionImpl@63187d63

    //测试数据库是否正常连接
    @Test
    void getConnection() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

}
