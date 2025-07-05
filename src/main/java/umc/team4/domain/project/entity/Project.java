package umc.team4.domain.project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import umc.team4.domain.fund.entity.Fund;
import umc.team4.domain.purchase.entity.Wallet;
import umc.team4.domain.user.entity.UserRole;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Column
    private String title;

    @Column
    private String summary;

    @Column
    private String description;

    @Column
    private String story;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column
    private String imageUrl;

    @Column
    private Long targetAmount;

    @Column
    private Long currentAmount;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Fund> funds;

    @CreatedDate  // 생성 시간 자동 설정
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // 수정 시간 자동 설정
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
}
