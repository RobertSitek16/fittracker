package com.fittracker.user.hateoas;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;

@Schema(description = "Response representation of a user")
public class UserModel extends RepresentationModel<UserModel> {
    @Schema(description = "Unique ID of the user", example = "1")
    private Long id;
    @Schema(description = "Username of the user", example = "john_doe")
    private String name;
    @Schema(description = "Email address of the user", example = "john@example.com")
    private String email;

    public UserModel(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UserModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
