package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.adapter;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.task.AbstractTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants.ParamNames;
import static by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.utils.Constants.MASTER_LIST_TABLE_PREFIX;

public class AdapterTask extends AbstractTask {
    private static final String START_PATH = "/start";
    private static final String STATUS_PATH = "/status";
    private static final String STOP_PATH = "/stop";
    public static final String URL_PARAM_ID = "3";

    @Override
    public void run() {
        state = STATE_RUNNING;

        String executionId = taskParameters.get(ParamNames.CURRENT_EXECUTION_ID);
        String nodeName = taskParameters.get(ParamNames.NODE_NAME);
        String masterListTable = MASTER_LIST_TABLE_PREFIX + executionId;
        String startAdapterURL = taskParameters.get(ParamNames.NODE_PARAM_PREFIX + URL_PARAM_ID) + START_PATH;

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
        AdapterStatus adapterExeuctionStatus = getAdapterStatus();
        while (AdapterStatus.RUNNING.equals(adapterExeuctionStatus)) {
            logger.debug("Node {} adapter status: {}", nodeName, adapterExeuctionStatus);
            try {
                Thread.sleep(POLL_INTERVAL);
                if (state != STATE_RUNNING) {
                    logger.debug("Adapter {} was aborted during response waiting", nodeName);
                    break;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            adapterExeuctionStatus = getAdapterStatus();
        }
    }

    private AdapterStatus getAdapterStatus() {
        String statusAdapterURL = taskParameters.get(ParamNames.NODE_PARAM_PREFIX + URL_PARAM_ID) + STATUS_PATH;
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

        String adapterURL = taskParameters.get(ParamNames.NODE_PARAM_PREFIX + URL_PARAM_ID) + STOP_PATH;
        logger.debug("Sending start to {} masterListTable: {}", adapterURL, masterListTable);
        ResponseEntity<AdapterResponse> response = restTemplate.postForEntity(
            adapterURL,
            new AdapterRequestDTO(masterListTable),
            AdapterResponse.class
        );
        System.out.println(response.getBody());

    }

    @Override
    public void forceComplete() {
        cancel();
        super.forceComplete();
    }

    private static final Logger logger = LoggerFactory.getLogger(AdapterTask.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private static final long POLL_INTERVAL = 3000L;
}
