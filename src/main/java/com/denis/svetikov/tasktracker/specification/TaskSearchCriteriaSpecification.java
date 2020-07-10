package com.denis.svetikov.tasktracker.specification;

import com.denis.svetikov.tasktracker.model.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


import java.sql.Date;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class TaskSearchCriteriaSpecification extends BaseSpecification<Task, TaskSearchCriteria> {

    @Override
    public Specification<Task> getFilter(TaskSearchCriteria request) {
        return (root, query, cb) -> {
            return where(
                    where(taskTitleContains(request.getTitle())))
                    .and(taskDescriptionContains(request.getDescription()))
                    .and(taskFilterByStatus(request.getStatus()))
                    .and(taskFilterByUser(request.getUser()))
                    .and(taskFilterByDateCreated(request.getDate(), request.getOperation()))
                    .toPredicate(root, query, cb);
        };

    }

    private Specification<Task> taskAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }

            return cb.like(
                    cb.lower(root.get(attribute)),
                    containsLowerCase(value)
            );
        };
    }

    private Specification<Task> taskEntityIdEqual(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }

            return cb.equal(
                    root.get(attribute).get("id"),
                    Integer.valueOf(value)
            );
        };
    }

    private Specification<Task> taskDateGreaterThen(String attribute, Date date) {
        return (root, query, cb) -> {
            if (date == null) {
                return null;
            }

            return cb.greaterThan(
                    root.get(attribute),
                    date
            );
        };
    }

    private Specification<Task> taskDateLessThen(String attribute, Date date) {
        return (root, query, cb) -> {
            if (date == null) {
                return null;
            }

            return cb.lessThan(
                    root.get(attribute),
                    date
            );
        };
    }

    private Specification<Task> taskDateEqual(String attribute, Date date) {
        return (root, query, cb) -> {
            if (date == null) {
                return null;
            }

            return cb.equal(
                    root.get(attribute),
                    date
            );
        };
    }

    private Specification<Task> taskTitleContains(String title) {
        return taskAttributeContains("title", title);
    }

    private Specification<Task> taskDescriptionContains(String desc) {
        return taskAttributeContains("description", desc);
    }

    private Specification<Task> taskFilterByStatus(String value) {
        return taskEntityIdEqual("status", value);
    }

    private Specification<Task> taskFilterByUser(String value) {
        return taskEntityIdEqual("user", value);
    }

    private Specification<Task> taskFilterByDateCreated(Date date, String operation) {

        if (operation == null || operation.isEmpty() || date == null) {
            return null;
        } else if (operation.equalsIgnoreCase(">")) {
            return taskDateGreaterThen("created", date);
        } else if (operation.equalsIgnoreCase("<")) {
            return taskDateLessThen("created", date);
        } else if (operation.equalsIgnoreCase(":")) {
            return taskDateEqual("created", date);
        }

        throw new IllegalArgumentException("Operation parameter is not valid");
    }

}
