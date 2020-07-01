package com.denis.svetikov.tasktracker.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO class for register request.
 *
 * @author Denis Svetikov
 * @version v2
 */

@Data
public class AuthenticationRegisterRequestDto {

    @NotBlank(message = "Username can't be null")
    private String username;
    @NotBlank(message = "Email can't be null")
    private String email;
    @NotBlank(message = "First Name can't be null")
    private String firstName;
    @NotBlank(message = "Last Name can't be null")
    private String lastName;
    @Size(min = 6,max = 15, message = "Password should be at least 6 symbols and not more than 15")
    private String password;

}
