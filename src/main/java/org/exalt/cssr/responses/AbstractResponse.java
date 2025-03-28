package org.exalt.cssr.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractResponse {
    protected ResponseStatusType responseStatus;
    protected String message;
    protected Integer code;
}
