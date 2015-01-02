package com.sharathp.service.symptom_management.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;

@Immutable
@Entity
public class PatientCheckIn {

    @Id
    @GeneratedValue(generator="uuid2-generator")
    @GenericGenerator(name="uuid2-generator", strategy = "uuid2")
    @org.hibernate.annotations.Type(type="pg-uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Patient patient;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(final Patient patient) {
        this.patient = patient;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }
}
