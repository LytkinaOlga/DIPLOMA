package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.adapter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

@Configuration
public class AdapterLibraryConfig {
    @Autowired
    DBConfiguration dbConfiguration;

//    @Lazy
//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public DataSource customDataSource() {
//        DataSourceBuilder<?> dsBuilder = DataSourceBuilder.create();
//        if (dbConfiguration.getDbVendor() != null) {
//            dsBuilder.driverClassName(dbConfiguration.getDbVendor().getDriverClass());
//        }
//        dsBuilder.url(dbConfiguration.getJdbcUrl());
//        dsBuilder.username(dbConfiguration.getUsername());
//        dsBuilder.password(dbConfiguration.getPassword());
//        return dsBuilder.build();
//    }
}
