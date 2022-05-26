package br.com.luizaugustocs.teambuilder.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;

@Data
public class ErrorDescription {

    private LocalDateTime time;
    private String message;
    private String path;
    private HttpStatus status;

    public ErrorDescription(HttpStatus status, String message) {
        this.time = LocalDateTime.now();
        this.path = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
        this.status = status;
        this.message = message;
    }
}
