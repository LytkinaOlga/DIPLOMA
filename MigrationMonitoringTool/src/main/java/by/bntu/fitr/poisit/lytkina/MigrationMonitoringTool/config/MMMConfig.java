package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.config;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.ExecutionGraph;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.GraphNode;
import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution.NodeExecutionWrapper;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Collection;

@Configuration
public class MMMConfig {
    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public NodeExecutionWrapper getNodeExecutionWrapper(ExecutionGraph executionGraph, GraphNode graphNode) {
        return new NodeExecutionWrapper(executionGraph, graphNode);
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public ExecutionGraph getExecutionGraph(Long executionId, Collection<GraphNode> startNodes) {
        return new ExecutionGraph(executionId, startNodes);
    }
}