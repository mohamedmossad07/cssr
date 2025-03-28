package org.exalt.cssr.responses;

import lombok.*;
import org.springframework.http.HttpStatus;

/**
 *  Api Failure Response
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FailureResponse extends AbstractResponse {
    /**
     *  Api Failure Response
     * @param message reasonable message to user
     * @param code API status code
     */
    public FailureResponse(String message, HttpStatus code) {
        super(ResponseStatusType.FAILED, message, code.value());
    }
}
