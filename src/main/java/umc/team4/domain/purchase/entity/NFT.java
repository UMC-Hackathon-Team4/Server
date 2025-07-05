package umc.team4.domain.purchase.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "nft")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class NFT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nft_id")
    private Long nftId;

    @Column
    private String tokenURL;

    @OneToOne(mappedBy = "nft", fetch = FetchType.LAZY)
    private Purchase purchase;

    @CreatedDate  // 생성 시간 자동 설정
    @Column(name = "mintedAt", nullable = false)
    private LocalDateTime mintedAt;

}
