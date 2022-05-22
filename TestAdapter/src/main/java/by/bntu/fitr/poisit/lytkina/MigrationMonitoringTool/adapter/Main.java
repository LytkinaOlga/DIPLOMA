package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

//@SpringBootApplication(
//    exclude={
//    DataSourceAutoConfiguration.class,
//    DataSourceTransactionManagerAutoConfiguration.class
//})
//@ComponentScan(value = {
//    "by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.adapter.*",
//    "by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.adapter",
//}, excludeFilters = {
//    @ComponentScan.Filter(type= FilterType.REGEX,
//        pattern="org.springframework.boot.autoconfigure.sql.init.*"
//    )
//})



@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
