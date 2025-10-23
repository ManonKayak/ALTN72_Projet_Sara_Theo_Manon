package org.example.altn72_projet_sara_theo_manon.model;

import jakarta.persistence.*;

@Entity
@Table(name = "memoire", schema = "projectDB")
public class Memoire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "sujet", nullable = false)
    private String sujet;

    @Column(name = "note")
    private Float note;

    @Lob
    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "date_soutenance")
    private Integer dateSoutenance;

    @Column(name = "note_soutenance")
    private Float noteSoutenance;

    @Lob
    @Column(name = "commentaire_soutenance")
    private String commentaireSoutenance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public Float getNote() {
        return note;
    }

    public void setNote(Float note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Integer getDateSoutenance() {
        return dateSoutenance;
    }

    public void setDateSoutenance(Integer dateSoutenance) {
        this.dateSoutenance = dateSoutenance;
    }

    public Float getNoteSoutenance() {
        return noteSoutenance;
    }

    public void setNoteSoutenance(Float noteSoutenance) {
        this.noteSoutenance = noteSoutenance;
    }

    public String getCommentaireSoutenance() {
        return commentaireSoutenance;
    }

    public void setCommentaireSoutenance(String commentaireSoutenance) {
        this.commentaireSoutenance = commentaireSoutenance;
    }

}