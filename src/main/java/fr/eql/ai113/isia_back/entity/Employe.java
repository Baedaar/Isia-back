package fr.eql.ai113.isia_back.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Employe extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private LocalDate dateRetrait;
    @JsonIgnore
    @OneToMany
    private List<Document> employesDoc;

    @ManyToOne
    @JoinColumn(name = "lieu_naissance_id")
    private LieuNaissance lieuNaissance;

    @OneToOne
    @JoinColumn(name = "adresse_id",referencedColumnName = "id")
    private Adresse adresse;

    public Employe(String username, String password, Collection<Role> roles, String nom, String prenom, LocalDate dateNaissance, LocalDate dateRetrait, LieuNaissance lieuNaissance, Adresse adresse, List<Document> documents) {
        super(username, password, roles);
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.dateRetrait = dateRetrait;
        this.lieuNaissance = lieuNaissance;
        this.adresse = adresse;
        this.employesDoc= documents;
    }

    public Employe() {

    }



    // getters
    public Long getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public LocalDate getDateNaissance() {
        return dateNaissance;
    }
    public LocalDate getDateRetrait() {
        return dateRetrait;
    }
    public LieuNaissance getLieuNaissance() {
        return lieuNaissance;
    }
    public Adresse getAdresse() {
        return adresse;
    }
    public List<Document> getEmployesDoc() {
        return employesDoc;
    }

    // setters
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public void setDateRetrait(LocalDate dateRetrait) {
        this.dateRetrait = dateRetrait;
    }
    public void setLieuNaissance(LieuNaissance lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }
    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }
    public void setEmployesDoc(List<Document> employesDoc) {
        this.employesDoc = employesDoc;
    }
}
