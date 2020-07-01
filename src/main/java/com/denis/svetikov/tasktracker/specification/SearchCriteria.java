package com.denis.svetikov.tasktracker.specification;

import lombok.Data;

@Data
public class SearchCriteria {

    private String filter;
    private String value;
    private String sort;
    private String order;

}
