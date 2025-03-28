package org.exalt.cssr.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse<Payload> extends AbstractResponse{
    private Payload data;

    public SuccessResponse(String message, HttpStatus code, Payload data) {
        super(ResponseStatusType.SUCCESS, message, code.value());
        this.data = data;
    }
}
