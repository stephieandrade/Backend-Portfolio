package project.Shockbyte.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeDTO {

    private Long id;
    private LocalDateTime systemUptime;
    private Long allocatedRAM;
    private Long totalRAM;
    private Long totalDisk;
    private Long allocatedDisk;


}
