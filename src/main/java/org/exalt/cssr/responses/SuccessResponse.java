package org.exalt.cssr.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SuccessResponse<Payload> extends AbstractResponse {
    private final Payload data;

    public SuccessResponse(String message, Payload data) {
        super(true, message);
        this.data = data;
    }

    public SuccessResponse(Payload data) {
        this(null, data);
    }
}
