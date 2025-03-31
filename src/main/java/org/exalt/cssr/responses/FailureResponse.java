package org.exalt.cssr.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Api Failure Response
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FailureResponse extends AbstractResponse {
    /**
     * Api Failure Response
     *
     * @param message reasonable message to user
     */
    public FailureResponse(String message) {
        super(false, message);
    }
}
