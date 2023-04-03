package fr.eql.ai113.isia_back.entity;

import javax.persistence.*;

@Entity
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numeroRue;
    private String rue;
    private String ville;

    @OneToOne
    private Employe employe;


    public Adresse(Integer numeroRue, String rue, String ville) {
        this.numeroRue = numeroRue;
        this.rue = rue;
        this.ville = ville;
    }

    public Adresse() {
        super();
    }

    // Getters
    public Long getId() {
        return id;
    }
    public Integer getNumeroRue() {
        return numeroRue;
    }
    public String getRue() {
        return rue;
    }
    public String getVille() {
        return ville;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setNumeroRue(Integer numeroRue) {
        this.numeroRue = numeroRue;
    }
    public void setRue(String rue) {
        this.rue = rue;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }
}
