package org.exalt.cssr.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class SuccessResponse<Payload> extends AbstractResponse {
    private final Payload data;

    public SuccessResponse(String message, Payload data) {
        super(true, message);
        this.data = data;
    }
    public SuccessResponse(Payload data) {
        this(null,data);
    }
}
