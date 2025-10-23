package org.example.altn72_projet_sara_theo_manon.model;

import jakarta.persistence.*;

@Entity
@Table(name = "entreprise", schema = "projectDB")
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "raison_sociale", nullable = false)
    private String raisonSociale;

    @Lob
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @Lob
    @Column(name = "infos")
    private String infos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

}