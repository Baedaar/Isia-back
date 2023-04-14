package fr.eql.ai113.isia_back.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.Collection;

@Entity
public class Employe implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private LocalDate dateRetrait;

    @ManyToOne
    @JoinColumn(name = "lieu_naissance_id")
    private LieuNaissance lieuNaissance;

    @OneToOne
    @JoinColumn(name = "adresse_id",referencedColumnName = "id")
    private Adresse adresse;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

    public Employe(String username, String password, String nom, String prenom, LocalDate dateNaissance, LieuNaissance lieuNaissance, Adresse adresse) {
        this.username = username;
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

    public Employe(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }





    // Getters
    public Long getId() {
        return id;
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
    public LocalDate getDateRetrait() {
        return dateRetrait;
    }

    //Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setUsername(String login) {
        this.username = login;
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
    public void setDateRetrait(LocalDate dateRetrait) {
        this.dateRetrait = dateRetrait;
    }
}
