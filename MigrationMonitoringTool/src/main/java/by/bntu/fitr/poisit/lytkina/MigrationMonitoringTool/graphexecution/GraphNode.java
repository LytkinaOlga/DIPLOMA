package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.graphexecution;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;
@Data
@EqualsAndHashCode(of = "nodeId")
@NoArgsConstructor
public class GraphNode {
    private Long nodeId;
    private Collection<GraphNode> incomingNodes;
    private Collection<GraphNode> outgoingNodes;
    private String name;

    public GraphNode(Collection<GraphNode> incomingNodes, Collection<GraphNode> outgoingNodes) {
        this.incomingNodes = incomingNodes;
        this.outgoingNodes = outgoingNodes;
    }

    public GraphNode(String name, Collection<GraphNode> incomingNodes) {
        this.name = name;
        this.incomingNodes = incomingNodes;
        this.nodeId = (long) name.charAt(0);
    }
}
