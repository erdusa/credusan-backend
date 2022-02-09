package com.credusan;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

public class TestSqlUtils {

    public static void executeQuery(String... sqls) {
        DataSource dataSource = new DriverManagerDataSource("jdbc:h2:mem:testdb", "sa", "password");

        Arrays.stream(sqls).forEach(sql -> {
            try {
                ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("sql-scripts/" + sql));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }
}
