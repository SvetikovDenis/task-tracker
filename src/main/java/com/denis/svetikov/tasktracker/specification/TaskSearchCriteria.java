package com.denis.svetikov.tasktracker.specification;

import lombok.Data;

import java.sql.Date;
import java.util.Optional;

@Data
public class TaskSearchCriteria {

    private String title;
    private String description;
    private String status;
    private String user;
    private Date date;
    private String operation;
    private String sort;
    private String order;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(Optional.ofNullable(title).map(s -> "title=" + s + ",").orElse(""));
        builder.append(Optional.ofNullable(description).map(s -> "description=" + s + ",").orElse(""));
        builder.append(Optional.ofNullable(status).map(s -> "status=" + s + ",").orElse(""));
        builder.append(Optional.ofNullable(sort).map(s -> "sort=" + s + ",").orElse(""));
        builder.append(Optional.ofNullable(date).map(s -> "date=" + s + ",").orElse(""));
        builder.append(Optional.ofNullable(order).map(s -> "order=" + s + ",").orElse(""));
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }
}