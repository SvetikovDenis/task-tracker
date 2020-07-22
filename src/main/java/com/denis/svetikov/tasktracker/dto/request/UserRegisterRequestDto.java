package com.denis.svetikov.tasktracker.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * DTO class for register request.
 *
 * @author Denis Svetikov
 * @version v2
 */

@Data
public class UserRegisterRequestDto {

    @NotBlank(message = "username can't be null or blank")
    @Size(min = 5, max = 15, message = "username must be at least 5 characters long and not more than 15")
    @Pattern(regexp = "^[aA-zZ]\\w{5,15}$",
            message = "Please provide valid username")
    @Column(name = "username")
    private String username;

    @Email(message = "You must provide a valid email address")
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(min = 3, max = 30)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 30)
    @Column(name = "last_name")
    private String lastName;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long be of mixed case and also contain a digit and a special symbol.")
    @Column(name = "password")
    private String password;

    public UserRegisterRequestDto() {
    }

    @Builder
    public UserRegisterRequestDto(String username,String email,String firstName,String lastName,String password) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }


}
