package umc.team4.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("함:께 - 장애인 창작자 프로젝트 플랫폼 API")
                        .description("""
                        장애인과 비장애인이 함께하는 창작 프로젝트 플랫폼 '함:께'의 OpenAPI 문서입니다.

                        ✅ 주요 기능:
                        - 프로젝트 등록
                        - 전체/카테고리별 프로젝트 조회
                        - 프로젝트 상세 조회
                        - 함께하기(펀딩하기)

                        """)
                        .version("1.0.0"));
    }

}
