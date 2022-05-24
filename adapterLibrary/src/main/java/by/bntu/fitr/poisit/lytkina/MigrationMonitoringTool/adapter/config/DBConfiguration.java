package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.adapter.config;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.adapter.rest.ConfigureAdapterDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class DBConfiguration {
    private String username;
    private String password;
    private String jdbcUrl;
    private DBVendor dbVendor;

    public void update(ConfigureAdapterDTO configureAdapterDTO) {
        this.username = configureAdapterDTO.getUsername();
        this.password = configureAdapterDTO.getPassword();
        this.jdbcUrl = configureAdapterDTO.getJdbcUrl();
        this.dbVendor = configureAdapterDTO.getDbVendor();
    }

}
