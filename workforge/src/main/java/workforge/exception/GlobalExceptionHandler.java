package workforge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String,String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(),error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(new ErrorResponse(400,"Validation failed",errors));
    }

    @ExceptionHandler(DuplicateSlugException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateSlugException(DuplicateSlugException exception) {
        return ResponseEntity.status(409).body(new ErrorResponse(409,exception.getMessage(),null));
    }

    @ExceptionHandler(OrganizationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrganizationNotFoundException(OrganizationNotFoundException exception) {
        return ResponseEntity.status(404).body(new ErrorResponse(404,exception.getMessage(),null));
    }

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTeamNotFoundException(TeamNotFoundException exception) {
        return ResponseEntity.status(404).body(new ErrorResponse(404,exception.getMessage(),null));
    }

    @ExceptionHandler(DuplicateTeamSlugException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateTeamSlugException(DuplicateTeamSlugException exception) {
        return ResponseEntity.status(409).body(new ErrorResponse(409,exception.getMessage(),null));
    }

    @ExceptionHandler(DuplicateProjectSlugException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateProjectSlug(DuplicateProjectSlugException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(409,ex.getMessage(),null));
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProjectNotFound(ProjectNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404,ex.getMessage(),null));
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFound(TaskNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, ex.getMessage(), null));
    }

}
