package org.example.altn72_projet_sara_theo_manon.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tuteur", schema = "projectDB")
public class Tuteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "poste", nullable = false)
    private String poste;

    @Lob
    @Column(name = "nom", nullable = false)
    private String nom;

    @Lob
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Lob
    @Column(name = "email", nullable = false)
    private String email;

    @Lob
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Lob
    @Column(name = "remarques", nullable = false)
    private String remarques;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entreprise_id")
    private Entreprise entreprise;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

}