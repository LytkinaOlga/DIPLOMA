package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.adapter.config;

public enum DBVendor {
    POSTGRES("org.postgresql.Driver"),
    ORACLE("oracle.jdbc.driver.OracleDriver"),
    MYSQL("com.mysql.jdbc.Driver");
    private String driverClass;

    DBVendor(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getDriverClass() {
        return driverClass;
    }
}
