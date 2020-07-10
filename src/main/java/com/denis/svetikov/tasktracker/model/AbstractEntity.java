package com.denis.svetikov.tasktracker.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Base class with property 'id'.
 * Used as a base class for all objects that requires this property.
 *
 * @author Denis Svetikov
 * @version v2
 */

@MappedSuperclass
@Data
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created")
    private Timestamp created;

    @Column(name = "updated")
    private Timestamp updated;

    @PrePersist
    public void toCreate() {
        setCreated(new Timestamp(System.currentTimeMillis()));
    }

    @PreUpdate
    public void toUpdate() {
        setUpdated(new Timestamp(System.currentTimeMillis()));
    }

}
