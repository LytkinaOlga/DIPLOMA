package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.concurrent.CompletableFuture;

@Component
public class SQLAdapter implements Adapter {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Value("${sqlfile:sqladapter.sql}")
    private String sqlFileName;

    private volatile AdapterStatus status = AdapterStatus.NOT_STARTED;
    private volatile int stopStatus = 0;

    @Override
    public AdapterResponse start(String masterListTable) {
        if (AdapterStatus.RUNNING.equals(status)) {
            return AdapterResponse.FAIL;
        }

        status = AdapterStatus.RUNNING;

        CompletableFuture.runAsync(() -> {
            try {
                String sql = fileAsString(sqlFileName);
                sql = sql.replace("{ML_TABLE}", masterListTable);
                String[] sqlStatements = sql.split("/");
                for (String sqlStatement : sqlStatements) {
                    if (stopStatus == 0) {
                        jdbcTemplate.execute(sqlStatement);
                    } else {
                        System.out.println("Adapter stopped - will not run next statement, last was " + sqlStatement);
                        status = AdapterStatus.FAILED;
                        stopStatus = 0;
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                status = AdapterStatus.FAILED;
            }
            status = AdapterStatus.SUCCEEDED;
        });

        return AdapterResponse.OK;
    }

    @Override
    public AdapterResponse stop() {
        stopStatus = 1;
        System.out.println("Adapter got stop request - next statement will be not executed");
        return AdapterResponse.OK;
    }

    @Override
    public AdapterStatus getStatus() {
        return status;
    }

    public static String fileAsString(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
