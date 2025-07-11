package umc.team4.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        Server httpsServer = new Server();
        httpsServer.setUrl("https://togetherumc.kro.kr");
        httpsServer.setDescription("Production HTTPS Server");

        Info apiInfo = new Info()
                .title("함:께 - 장애인 창작자 프로젝트 플랫폼 API")
                .description("""
                        장애인과 비장애인이 함께하는 창작 프로젝트 플랫폼 '함:께'의 OpenAPI 문서입니다.

                        ✅ 주요 기능:
                        - 프로젝트 등록
                        - 전체/카테고리별 프로젝트 조회
                        - 프로젝트 상세 조회
                        - 함께하기(펀딩하기)
                        """)
                .version("1.0.0");

        return new OpenAPI()
                .servers(List.of(httpsServer))
                .info(apiInfo);
    }
}
