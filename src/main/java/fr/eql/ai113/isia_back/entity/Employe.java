package fr.eql.ai113.isia_back.entity;

import javax.persistence.*;

import java.time.LocalDate;

@Entity
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String password;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;

    @ManyToOne
    @JoinColumn(name = "lieu_naissance_id")
    private LieuNaissance lieuNaissance;

    @OneToOne
    @JoinColumn(name = "adresse_id",referencedColumnName = "id")
    private Adresse adresse;

    public Employe(String login, String password, String nom, String prenom, LocalDate dateNaissance, LieuNaissance lieuNaissance, Adresse adresse) {
        this.login = login;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.adresse = adresse;
    }

    public Employe() {
        super();
    }


    // Getters
    public Long getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
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
    public LieuNaissance getLieuNaissance() {
        return lieuNaissance;
    }
    public Adresse getAdresse() {
        return adresse;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public void setLieuNaissance(LieuNaissance lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }
    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }
}
