package com.example.slmiddleware.service;


import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;

public class OracleTest {
    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnection() {
        try {
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String userName = "system";
            String userPassword = "1234";
            Connection c = DriverManager.getConnection(url, userName, userPassword);
            System.out.println(c);
        }catch(Exception e) {
            fail(e.getMessage());
        }
    }
}
