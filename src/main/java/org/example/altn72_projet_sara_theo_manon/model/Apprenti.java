package org.example.altn72_projet_sara_theo_manon.model;

import jakarta.persistence.*;

@Entity
@Table(name = "apprenti", schema = "projectDB")
public class Apprenti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "annee_academique", nullable = false)
    private Integer anneeAcademique;

    @Column(name = "majeure", nullable = false)
    private Integer majeure;

    @Lob
    @Column(name = "nom", nullable = false)
    private String nom;

    @Lob
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Lob
    @Column(name = "mail", nullable = false)
    private String mail;

    @Lob
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Lob
    @Column(name = "remarques", nullable = false)
    private String remarques;

    @Column(name = "niveau", nullable = false)
    private Integer niveau;

    @Column(name = "archive", nullable = false)
    private Boolean archive = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entreprise_id", nullable = false)
    private Entreprise entreprise;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tuteur_id", nullable = false)
    private Tuteur tuteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memoire_id")
    private Memoire memoire;

    public Memoire getMemoire() {
        return memoire;
    }

    public void setMemoire(Memoire memoire) {
        this.memoire = memoire;
    }

    public Tuteur getTuteur() {
        return tuteur;
    }

    public void setTuteur(Tuteur tuteur) {
        this.tuteur = tuteur;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(Integer anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public Integer getMajeure() {
        return majeure;
    }

    public void setMajeure(Integer majeure) {
        this.majeure = majeure;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRemarques() {
        return remarques;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }

    public Integer getNiveau() {
        return niveau;
    }

    public void setNiveau(Integer niveau) {
        this.niveau = niveau;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

}