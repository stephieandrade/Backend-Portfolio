package project.Shockbyte.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.assertj.core.internal.Bytes;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "node")
public class Node {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column
    private LocalDateTime systemUptime;

    @Getter @Setter
    @Column(nullable = false)
    private Long allocatedRAM; //HERE

    @Getter @Setter
    @Column(nullable = false)
    private Long totalRAM; //HERE

    @Getter @Setter
    @Column(nullable = false)
    private Long totalDisk; //HERE

    @Getter @Setter
    @Column(nullable = false)
    private Long allocatedDisk; //HERE


}
