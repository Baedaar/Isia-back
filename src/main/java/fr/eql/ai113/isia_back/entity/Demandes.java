package fr.eql.ai113.isia_back.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Demandes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String objetDemande;

    private String description;


    public Demandes(String objetDemande, String description) {
        this.objetDemande = objetDemande;
        this.description = description;
    }

    public Demandes() {
        super();
    }


    // Getters
    public Long getId() {
        return id;
    }
    public String getObjetDemande() {
        return objetDemande;
    }
    public String getDescription() {
        return description;
    }


    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setObjetDemande(String objetDemande) {
        this.objetDemande = objetDemande;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
