package com.denis.svetikov.tasktracker.model;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    @NotBlank(message = "username can't be null or blank")
    @Size(min = 5 , max = 15 , message = "username must be at least 5 characters long and not more than 15")
    @Pattern(regexp = "^[aA-zZ]\\w{5,15}$",
            message = "Please provide valid username")
    @Column(name = "username")
    private String username;

    @NotBlank
    @Size(min = 3, max = 30)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Size(min = 3,max = 30)
    @Column(name = "last_name")
    private String lastName;

    @Email(message = "You must provide a valid email address")
    @Column(name = "email")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$" ,
            message = "Password must be at least 8 characters long be of mixed case and also contain a digit and a special symbol.")
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;


    public User() {
    }

    @Builder
    public User(Long id,String username,String firstName,String lastName,String email,String password,List<Role> roles) {
        this.setId(id);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

}
