package org.exalt.cssr.users;

import com.aerospike.client.query.IndexCollectionType;
import com.aerospike.client.query.IndexType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.aerospike.annotation.Indexed;
import org.springframework.data.aerospike.mapping.Document;
import org.springframework.data.annotation.Id;

import static io.swagger.v3.oas.annotations.media.Schema.*;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

/**
 * Represents a user in the system (either a driver or an owner)
 */
@Document(collection = "users")
@Schema(description = "User entity representing either a driver or car owner")
@Data
@Builder
public class User {
    @Id
    @Schema(description = "Unique identifier of the user", example = "usr123" ,requiredMode = NOT_REQUIRED)
    private String id;

    @Schema(description = "Username[firstname+lastname] for the user[Driver/Owner]", example = "Mohamed Mossad", requiredMode = REQUIRED)
    @NotEmpty
    @Indexed(type = IndexType.STRING)
    private String username;

    @Schema(description = "Email address", example = "mohamed@example.com", requiredMode = NOT_REQUIRED)
    @Indexed(type = IndexType.STRING)
    private String email;

    @Schema(description = "User Phone number ", example = "01061218341", requiredMode = REQUIRED)
    @NotEmpty
    @Pattern(regexp = "01[0-9]{9}")
    private String phone;

    @Schema(description = "Type of user (DRIVER or OWNER)", example = "DRIVER", requiredMode = REQUIRED)
    @NotNull
    private UserType type;
}