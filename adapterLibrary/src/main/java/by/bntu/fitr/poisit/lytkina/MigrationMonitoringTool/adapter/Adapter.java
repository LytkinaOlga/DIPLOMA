package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.adapter;

public interface Adapter {
    AdapterResponse start(String masterListTable);
    AdapterResponse stop();
    AdapterStatus getStatus();
}
