package tech.lemnova.continuum_backend.exception;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private int status;
    private Instant timestamp;

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = Instant.now();
    }
}
