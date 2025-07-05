package umc.team4.common.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@AllArgsConstructor
public enum ErrorStatus {
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "페이지를 찾을 수 없습니다."),

    // 입력값 검증 관련 에러
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "VALID401", "입력값이 올바르지 않습니다."),

    // 멤버 관련 에러
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4001", "사용자가 없습니다."),
    EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USER4002", "이미 사용중인 이메일입니다."),

    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "CATEGORY4001", "해당 카테고리가 없습니다."),

    // 펀드 관련 에러
    FUND_NOT_FOUND(HttpStatus.BAD_REQUEST, "FUND4001", "해당 아이디의 펀드가 없습니다."),
    FUND_STOCK_EMPTY(HttpStatus.BAD_REQUEST, "FUND4002", "해당 펀드의 남아있는 재고가 없습니다."),
    INSUFFICIENT_USER_COIN(HttpStatus.BAD_REQUEST, "FUND4003", "해당 펀드를 사기 위한 유저의 코인이 부족합니다."),
    FUND_NOT_FOUND_FOR_PROJECT(HttpStatus.NOT_FOUND, "FUND4004", "해당 프로젝트에 등록된 리워드가 없습니다."),

    // 프로젝트 관련 에러
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "PROJECT4001", "존재하지 않는 프로젝트입니다."),
    NO_PROJECTS_AVAILABLE(HttpStatus.NOT_FOUND, "PROJECT4002", "추천할 프로젝트가 없습니다."),
    INVALID_PROJECT_TYPE(HttpStatus.BAD_REQUEST, "PROJECT4003", "유효하지 않은 type입니다. [detail, intro, story, reward] 중 하나여야 합니다."),

    GENERAL_ERROR(HttpStatus.BAD_REQUEST, "GENERAL4001", "일반 오류");

    private final HttpStatus status;
    private final String code;
    private final String message;

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))  //공백이 아닌 문자열만 통과
                .orElse(this.getMessage());
    }


}