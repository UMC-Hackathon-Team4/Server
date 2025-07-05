package umc.team4.domain.project.dto;

import lombok.*;
import umc.team4.domain.project.entity.Category;

import java.time.LocalDate;
import java.util.List;

public class ProjectRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Create {
        private Long userId;
        private Category category;
        private Long targetAmount;
        private LocalDate startDate;
        private LocalDate endDate;

        private String title;
        private String summary;
        private String description;
        private String story;
        private String imageUrl;

        private List<Reward> rewards;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class Reward {
            private String title;
            private Long stock;
            private Long price;
        }
    }
}

