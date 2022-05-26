package br.com.luizaugustocs.teambuilder.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDescription {

    private LocalDateTime time;
    private String message;
    private String path;
    private HttpStatus status;

    private List<String> violations;


    public ErrorDescription(HttpStatus status) {
        this.time = LocalDateTime.now();
        this.path = ServletUriComponentsBuilder.fromCurrentRequest().build().getPath();
        this.status = status;
    }

    public ErrorDescription(HttpStatus status, String message) {
        this(status);
        this.message = message;
    }
    public ErrorDescription(HttpStatus status, List<String> violations) {
        this(status);
        this.violations = violations;
        this.message = "Error while parsing the parameters.";
    }
}
