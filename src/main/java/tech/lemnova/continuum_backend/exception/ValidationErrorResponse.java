package tech.lemnova.continuum_backend.exception;

import java.time.Instant;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponse {

    private String message;
    private int status;
    private Map<String, String> errors;
    private Instant timestamp;

    public ValidationErrorResponse(
        String message,
        int status,
        Map<String, String> errors
    ) {
        this.message = message;
        this.status = status;
        this.errors = errors;
        this.timestamp = Instant.now();
    }
}
