package workforge.exception;

public class DuplicateTeamSlugException extends RuntimeException {
    public DuplicateTeamSlugException(String message) {
        super(message);
    }
}