package com.denis.svetikov.tasktracker.specification;

import com.denis.svetikov.tasktracker.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.sql.Date;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class UserSearchCriteriaSpecification extends BaseSpecification<User, UserSearchCriteria> {


    @Override
    public Specification<User> getFilter(UserSearchCriteria request) {

        return (root, query, cb) -> {
            return where(
                    where(userFilterByUserName(request.getUsername())))
                    .and(userFilterByEmail(request.getEmail()))
                    .and(userFilterByfirstName(request.getFirstName()))
                    .and(userFilterByLastName(request.getLastName()))
                    .and(userFilterByDateCreated(request.getDate(), request.getOperation()))
                    .toPredicate(root, query, cb);
        };
    }


    private Specification<User> userAttributeContains(String attribute, String value) {
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


    private Specification<User> userDateGreaterThen(String attribute, Date date) {
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

    private Specification<User> userDateLessThen(String attribute, Date date) {
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

    private Specification<User> userDateEqual(String attribute, Date date) {
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

    private Specification<User> userFilterByUserName(String username) {
        return userAttributeContains("username", username);
    }

    private Specification<User> userFilterByEmail(String email) {
        return userAttributeContains("email", email);
    }

    private Specification<User> userFilterByfirstName(String firstName) {
        return userAttributeContains("firstName", firstName);
    }

    private Specification<User> userFilterByLastName(String firstName) {
        return userAttributeContains("firstName", firstName);
    }

    private Specification<User> userFilterByDateCreated(Date date, String operation) {

        if (operation == null || operation.isEmpty() || date == null) {
            return null;
        } else if (operation.equalsIgnoreCase(">")) {
            return userDateGreaterThen("created", date);
        } else if (operation.equalsIgnoreCase("<")) {
            return userDateLessThen("created", date);
        } else if (operation.equalsIgnoreCase(":")) {
            return userDateEqual("created", date);
        }

        throw new IllegalArgumentException("Operation parameter is not valid");
    }


}
