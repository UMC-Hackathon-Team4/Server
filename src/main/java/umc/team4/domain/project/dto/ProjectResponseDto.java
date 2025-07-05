package umc.team4.domain.project.dto;

import lombok.Builder;
import lombok.Getter;
import umc.team4.domain.project.entity.Category;

import java.time.DateTimeException;
import java.time.LocalDate;

@Getter
public class ProjectResponseDto {

    @Getter
    @Builder
    public static class ProjectDetailDto {
        private Long projectId;
        private String projectTitle;
        private String imageUrl;
        private Category category;
        private String summary;
        private Long targetAmount;
        private Long currentAmount;
        private String creatorImageUrl;
        private String creatorDetail;
        private String creatorNickname;
        private Long supportersCount;
    }

    @Getter
    @Builder
    public static class ProjectSummaryDto {
        private Long projectId;
        private String projectTitle;
        private String imageUrl;
        private String category;
        private Long currentAmount;
        private Long targetAmount;
        private LocalDate endDate;
    }

}

