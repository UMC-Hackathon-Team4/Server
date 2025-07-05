package umc.team4.common.exception;


import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import umc.team4.common.response.ApiResponse;
import umc.team4.common.status.ErrorStatus;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler
    public ResponseEntity<ApiResponse> validation(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ConstraintViolationException 추출 도중 에러 발생"));

        return ApiResponse.onFailure(ErrorStatus.VALIDATION_ERROR, errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();

        String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        return ApiResponse.onFailure(ErrorStatus.VALIDATION_ERROR, errorMessage);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        e.printStackTrace();

        return ApiResponse.onFailure(ErrorStatus._NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        log.error("Unhandled Exception: ", e);
        return ApiResponse.onFailure((ErrorStatus._INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse> handleGeneralException(GeneralException e) {
        e.printStackTrace();

        return ApiResponse.onFailure(e.getErrorStatus(), e.getMessage());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, ConversionFailedException.class})
    public ResponseEntity<ApiResponse> handleConversionFailedException(Exception e) {
        return ApiResponse.onFailure((ErrorStatus._BAD_REQUEST));
    }
}