package com.mihair.analysis_machine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long patientUID;
    private Date timeTaken;
    private String info;


    public Note(Long patientUID, Date timeTaken, String info) {
        this.patientUID = patientUID;
        this.timeTaken = timeTaken;
        this.info = info;
    }

    public Note(Long patientUID, String info) {
        this(patientUID, Date.from(Instant.ofEpochMilli(System.currentTimeMillis())), info);
    }

    public Note() {
        this(0L, "New note created!");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientUID() {
        return patientUID;
    }

    public void setPatientUID(Long patientUID) {
        this.patientUID = patientUID;
    }

    public Date getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Date timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id) && Objects.equals(patientUID, note.patientUID) && Objects.equals(timeTaken, note.timeTaken) && Objects.equals(info, note.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patientUID, timeTaken, info);
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", patientUID=" + patientUID +
                ", timeTaken=" + timeTaken +
                ", info='" + info + '\'' +
                '}';
    }
}
