package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestAdapter implements Adapter {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private int statusCount = 4;

    @Override
    public AdapterResponse start(String masterListTable) {
        jdbcTemplate.execute("insert into test values ('" + masterListTable + "')");
        return AdapterResponse.OK;
    }

    @Override
    public AdapterResponse stop() {
        jdbcTemplate.execute("delete from test");
        return AdapterResponse.OK;
    }

    @Override
    public AdapterStatus getStatus() {
        statusCount--;
        if (statusCount <= 0) {
            return AdapterStatus.SUCCEEDED;
        } else {
            return AdapterStatus.RUNNING;
        }
    }
}
