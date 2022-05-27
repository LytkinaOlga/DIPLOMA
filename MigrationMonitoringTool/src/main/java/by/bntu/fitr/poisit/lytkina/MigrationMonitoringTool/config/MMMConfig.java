package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.config;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionGraph;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.GraphNode;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.NodeExecutionWrapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

@Configuration
public class MMMConfig {
    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public NodeExecutionWrapper getNodeExecutionWrapper(
        ExecutionGraph executionGraph, GraphNode graphNode, Long executionId
    ) {
        return new NodeExecutionWrapper(executionGraph, graphNode, executionId);
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ExecutionGraph getExecutionGraph(Long executionId, Collection<GraphNode> startNodes) {
        return new ExecutionGraph(executionId, startNodes);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        objectMapper.setDateFormat(df);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}