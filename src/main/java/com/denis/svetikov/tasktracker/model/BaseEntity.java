package com.denis.svetikov.tasktracker.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Base class with property 'id'.
 * Used as a base class for all objects that requires this property.
 *
 * @author Denis Svetikov
 * @version v2
 */

@MappedSuperclass
@Data
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created")
    @NotNull(message = "Create date must not be null")
    private Date created;

    @LastModifiedDate
    @Column(name = "updated")
    @NotNull(message = "Create date must not be null")
    private Date updated;

}
