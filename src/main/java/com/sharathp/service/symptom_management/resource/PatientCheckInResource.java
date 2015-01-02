package com.sharathp.service.symptom_management.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

public class PatientCheckInResource {
    @JsonIgnore
    private UUID id;

    @NotNull
    private Date createdAt;

    @JsonProperty
    public UUID getId() {
        return id;
    }

    @JsonIgnore
    public void setId(final UUID id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }
}
