package com.denis.svetikov.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Simple domain object that represents application user's role - ADMIN, USER, etc.
 *
 * @author Denis Svetikov
 * @version v2
 */

@Data
@Entity
@Table(name = "roles")
public class Role extends AbstractEntity {

    @NotBlank
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;


    @Override
    public String toString() {
        return "Role{" +
                "id: " + super.getId() + ", " +
                "name: " + name + "}";
    }


    public Role() {
    }

    @Builder
    public Role( String name, List<User> users) {
        this.name = name;
        this.users = users;
    }
}
