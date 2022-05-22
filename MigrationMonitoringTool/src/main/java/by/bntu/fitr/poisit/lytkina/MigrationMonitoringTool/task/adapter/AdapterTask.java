package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.adapter;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants.ParamNames;
import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants.MASTER_LIST_TABLE_PREFIX;

public class AdapterTask implements Task {
    private static final String START_PATH = "/start";
    private static final String STATUS_PATH = "/status";
    private static final String STOP_PATH = "/stop";

    @Override
    public void run(Map<String, String> taskParameters) {
        this.taskParameters = taskParameters;
        String executionId = taskParameters.get(ParamNames.CURRENT_EXECUTION_ID);
        String nodeName = taskParameters.get(ParamNames.NODE_NAME);
        String masterListTable = MASTER_LIST_TABLE_PREFIX + executionId;
        String startAdapterURL = taskParameters.get(ParamNames.ADAPTER_URL) + START_PATH;

        logger.debug("Node {} is sending start to {} masterListTable: {}",
            nodeName, startAdapterURL, masterListTable
        );
        AdapterResponse response = restTemplate.postForEntity(
            startAdapterURL,
            new AdapterRequestDTO(masterListTable),
            AdapterResponse.class
        ).getBody();

        waitTillFinished();

        logger.debug("Node {} adapter work finished", nodeName);
    }

    private void waitTillFinished() {
        String nodeName = taskParameters.get(ParamNames.NODE_NAME);
        AdapterStatus status = getAdapterStatus();
        while (AdapterStatus.RUNNING.equals(status)) {
            logger.debug("Node {} adapter status: {}", nodeName, status);
            try {
                Thread.sleep(POLL_INTERVAL);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            status = getAdapterStatus();
        }
    }

    private AdapterStatus getAdapterStatus() {
        String statusAdapterURL = taskParameters.get(ParamNames.ADAPTER_URL) + STATUS_PATH;
        ResponseEntity<AdapterStatus> response = restTemplate.getForEntity(
            statusAdapterURL,
            AdapterStatus.class
        );
        return response.getBody();
    }

    @Override
    public void cancel() {
        RestTemplate restTemplate = new RestTemplate();
        String executionId = taskParameters.get(ParamNames.CURRENT_EXECUTION_ID);
        String nodeName = taskParameters.get(ParamNames.NODE_NAME);
        String masterListTable = MASTER_LIST_TABLE_PREFIX + executionId + "_" + nodeName;

        String adapterURL = taskParameters.get(ParamNames.ADAPTER_URL) + STOP_PATH;
        logger.debug("Sending start to {} masterListTable: {}", adapterURL, masterListTable);
        ResponseEntity<AdapterResponse> response = restTemplate.postForEntity(
            adapterURL,
            new AdapterRequestDTO(masterListTable),
            AdapterResponse.class
        );
        System.out.println(response.getBody());

    }

    private static final Logger logger = LoggerFactory.getLogger(AdapterTask.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private Map<String, String> taskParameters;
    private static final long POLL_INTERVAL = 3000L;
}
