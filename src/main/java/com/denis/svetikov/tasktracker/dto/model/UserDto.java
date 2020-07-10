package com.denis.svetikov.tasktracker.dto.model;

import com.denis.svetikov.tasktracker.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * DTO class for user requests by ROLE_USER
 *
 * @author Denis Svetikov
 * @version 1.0
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto extends AbstractDto {


    @NotBlank(groups = {New.class} ,message = "username can't be null or blank")
    @Size(min = 5 , max = 15 , message = "username must be at least 5 characters long and not more than 15")
    @Pattern(regexp = "^[aA-zZ]\\w{5,15}$", message = "Please provide valid username")
    @JsonView({StandardView.class,DetailsView.class})
    private String username;

    @NotBlank(groups = {New.class}, message = "First Name can't be null or blank")
    @Size(min = 3, max = 30)
    @JsonView({StandardView.class,DetailsView.class})
    private String firstName;

    @NotBlank(groups = {New.class},message = "Last Name can't be null or blank")
    @Size(min = 3,max = 30)
    @JsonView({StandardView.class,DetailsView.class})
    private String lastName;

    @NotBlank(groups = {New.class}, message = "Email can't be null or blank")
    @Email(message = "You must provide a valid email address")
    @JsonView({StandardView.class,DetailsView.class})
    private String email;

}
