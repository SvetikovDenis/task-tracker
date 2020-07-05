package com.denis.svetikov.tasktracker.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * DTO class for register request.
 *
 * @author Denis Svetikov
 * @version v2
 */

@Data
public class UerRegisterRequestDto {

    @NotBlank(message = "Username can't be null or blank")
    private String username;
    @NotBlank(message = "Email can't be null or blank")
    private String email;
    @NotBlank(message = "First name can't be null or blank")
    private String firstName;
    @NotBlank(message = "Last Name can't be null or blank")
    private String lastName;
    @Size(min = 8,max = 15, message = "Password should be at least 6 symbols and not more than 15")
    private String password;

}
