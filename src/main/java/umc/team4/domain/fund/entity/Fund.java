package umc.team4.domain.fund.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import umc.team4.domain.project.entity.Project;
import umc.team4.domain.purchase.entity.Wallet;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "fund")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Fund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fundId;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Long price;

    @Column
    private Long stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(mappedBy = "fund", cascade = CascadeType.ALL)
    private List<Funding> fundings;

    @CreatedDate  // 생성 시간 자동 설정
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
}
