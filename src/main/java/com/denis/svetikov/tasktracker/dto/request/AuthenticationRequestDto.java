package com.denis.svetikov.tasktracker.dto.request;

import lombok.Data;

/**
 * DTO class for authentication (login) request.
 *
 * @author Denis Svetikov
 * @version v2
 */

@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
