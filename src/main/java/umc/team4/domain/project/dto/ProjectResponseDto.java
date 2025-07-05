package umc.team4.domain.project.dto;

import lombok.*;
import umc.team4.domain.project.entity.Category;

import java.time.LocalDate;
import java.util.List;

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
    @AllArgsConstructor
    public static class ProjectIntroDto {
        private Long projectId;
        private String description;
    }

    @Getter
    @AllArgsConstructor
    public static class ProjectStoryDto {
        private Long projectId;
        private String story;
    }

    @Getter
    @AllArgsConstructor
    public static class ProjectRewardDto {
        private Long projectId;
        private List<RewardDto> rewards;
    }

    @Getter
    @AllArgsConstructor
    public static class RewardDto {
        private Long fundId;
        private String title;
        private String description;
        private Long price;
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
        private String percentage;
        private LocalDate endDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProjectCreate {
        private Long projectId;
        private String title;
        private LocalDate startDate;
        private LocalDate endDate;
    }

}

