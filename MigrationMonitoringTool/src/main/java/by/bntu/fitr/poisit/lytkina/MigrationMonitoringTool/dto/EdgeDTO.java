package by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.dto;

import by.bntu.fitr.poisit.lytkina.MigrationMonitoringTool.model.jpa.EdgeJPA;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class EdgeDTO {
    @Positive
    @NotBlank
    private String id;
    private String source;
    private String target;

    public EdgeDTO(EdgeJPA edgeJPA) {
        this.id = edgeJPA.getId().toString();
        this.source = edgeJPA.getNodeFrom().toString();
        this.target = edgeJPA.getNodeTo().toString();
    }
}
