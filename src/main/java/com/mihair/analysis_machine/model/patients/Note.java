package com.mihair.analysis_machine.model.patients;

import jakarta.persistence.*;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="active_patients_notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UID;
    private Long patientUID;
    private Date timeTaken;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] title;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] info;


    public Note(Long patientUID, Date timeTaken, String title, String info) {
        this.patientUID = patientUID;
        this.timeTaken = timeTaken;
        this.info = info.getBytes(StandardCharsets.UTF_8);
        this.title = title.getBytes(StandardCharsets.UTF_8);
    }

    public Note(Long patientUID, String title, String info) {
        this(patientUID, Date.from(Instant.ofEpochMilli(System.currentTimeMillis())), title, info);
    }

    public Note() {
        this(0L,"N/A", "New note created!");
    }

    public Long getUID() {
        return UID;
    }

    public void setUID(Long id) {
        this.UID = id;
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

    public byte[] getInfo() {
        return info;
    }

    public void setInfo(byte[] info) {
        this.info = Arrays.copyOf(info,info.length);
    }


    public byte[] getTitle() {
        return this.title;
    }

    public void setTitle(byte[] title) {
        this.title = Arrays.copyOf(title,title.length);
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + UID +
                ", patientUID=" + patientUID +
                ", timeTaken=" + timeTaken +
                ", title='" + title + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(UID, note.UID) && Objects.equals(patientUID, note.patientUID) && Objects.equals(timeTaken, note.timeTaken) && Objects.equals(title, note.title) && Objects.equals(info, note.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UID, patientUID, timeTaken, Arrays.hashCode(title), Arrays.hashCode(info));
    }
}
