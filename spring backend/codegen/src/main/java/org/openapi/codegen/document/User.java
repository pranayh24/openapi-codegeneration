package org.openapi.codegen.document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
@Getter
@Setter
public class User extends BaseDocument {

    @NotBlank
    @Size(min = 1, max = 50)
    @Indexed(unique = true)
    private String username;

    @NotBlank
    @Email
    @Indexed(unique = true)
    private String email;

    @NotBlank
    private String password;

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    private Set<String> roles = new HashSet<>();

    @Field("is_active")
    private boolean isActive = true;

    @Field("is_email_verified")
    private boolean isEmailVerified = false;

    @Field("generation_count")
    private Long generationCount = 0L;

    @Field("total_cost")
    private Double totalCost = 0.0;

    public User() {
        super();
        this.roles.add("ROLE_USER");
    }

    public User(String username, String email, String password) {
        this();
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
