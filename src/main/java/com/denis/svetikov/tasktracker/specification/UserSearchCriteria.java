package com.denis.svetikov.tasktracker.specification;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.util.Optional;

@Data
public class UserSearchCriteria {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String sort;
    private String order;
    private String operation;
    private Date date;


    public UserSearchCriteria() {
    }

    @Builder
    public UserSearchCriteria(String username, String email, String firstName, String lastName, String sort, String order, String operation, Date date) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sort = sort;
        this.order = order;
        this.operation = operation;
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(Optional.ofNullable(username).map(s -> "username=" + s + ",").orElse(""));
        builder.append(Optional.ofNullable(email).map(s -> "email=" + s + ",").orElse(""));
        builder.append(Optional.ofNullable(firstName).map(s -> "firstName=" + s + ",").orElse(""));
        builder.append(Optional.ofNullable(lastName).map(s -> "lastName=" + s + ",").orElse(""));
        builder.append(Optional.ofNullable(sort).map(s -> "sort=" + s + ",").orElse(""));
        builder.append(Optional.ofNullable(order).map(s -> "order=" + s + ",").orElse(""));
        builder.append(Optional.ofNullable(operation).map(s -> "operation=" + s + ",").orElse(""));
        builder.append(Optional.ofNullable(date).map(s -> "date=" + s + ",").orElse(""));
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
