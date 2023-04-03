package fr.eql.ai113.isia_back.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LieuNaissance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paysNaissance;
    private String villeNaissance;


    public LieuNaissance(String paysNaissance, String villeNaissance) {
        this.paysNaissance = paysNaissance;
        this.villeNaissance = villeNaissance;
    }

    public LieuNaissance() {
        super();
    }

    // Getters
    public Long getId() {
        return id;
    }
    public String getPaysNaissance() {
        return paysNaissance;
    }
    public String getVilleNaissance() {
        return villeNaissance;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setPaysNaissance(String paysNaissance) {
        this.paysNaissance = paysNaissance;
    }
    public void setVilleNaissance(String villeNaissance) {
        this.villeNaissance = villeNaissance;
    }
}
