package org.example.altn72_projet_sara_theo_manon.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mission", schema = "projectDB")
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "mots_cles", nullable = false)
    private String motsCles;

    @Lob
    @Column(name = "metier_cible", nullable = false)
    private String metierCible;

    @Lob
    @Column(name = "commentaires", nullable = false)
    private String commentaires;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMotsCles() {
        return motsCles;
    }

    public void setMotsCles(String motsCles) {
        this.motsCles = motsCles;
    }

    public String getMetierCible() {
        return metierCible;
    }

    public void setMetierCible(String metierCible) {
        this.metierCible = metierCible;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

}