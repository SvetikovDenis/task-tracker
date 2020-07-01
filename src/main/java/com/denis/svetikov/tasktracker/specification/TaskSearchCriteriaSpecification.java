package com.denis.svetikov.tasktracker.specification;

import com.denis.svetikov.tasktracker.model.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class TaskSearchCriteriaSpecification extends BaseSpecification<Task, SearchCriteria> {


    @Override
    public Specification<Task> getFilter(SearchCriteria request) {
        return (root, query, cb) -> {


            if (request.getSort() != null) {

                if (request.getOrder().equals("asc") && request.getSort().equals("user")) {
                    query.orderBy(cb.asc(root.get("user").get("created")));
                }

                if (request.getOrder().equals("desc") && request.getSort().equals("user")) {
                    query.orderBy(cb.desc(root.get("user").get("created")));
                }
            }

            return where(
                    where(taskStatusEqual(request.getFilter(), request.getValue())))
                    .toPredicate(root, query, cb);
        };

    }


    private Specification<Task> taskStatusEqual(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }

            return cb.equal(
                    cb.lower(root.get(attribute).get("id")),
                    Integer.valueOf(value)
            );
        };
    }


}
