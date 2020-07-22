package com.denis.svetikov.tasktracker.dto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
public abstract class AbstractDto implements Serializable {

    @Null(groups = {New.class})
    @NotNull(groups = {Existing.class})
    @JsonView({StandardView.class, DetailsView.class})
    Long id;

    @Null(groups = {New.class, Existing.class})
    @JsonView({DetailsView.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    Timestamp created;

    @Null(groups = {New.class, Existing.class})
    @JsonView({DetailsView.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    Timestamp updated;


    public interface New {
    }

    public interface Existing {
    }

    public interface StandardView {
    }

    public interface DetailsView {

    }

}
