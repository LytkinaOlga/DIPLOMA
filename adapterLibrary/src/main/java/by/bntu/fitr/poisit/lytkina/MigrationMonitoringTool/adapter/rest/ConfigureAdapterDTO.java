package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.adapter.rest;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.adapter.config.DBVendor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConfigureAdapterDTO {
    private DBVendor dbVendor;
    private String username;
    private String password;
    private String jdbcUrl;
}
