package fr.eql.ai113.isia_back.entity.dto;

import java.time.LocalDate;

public class EmployeDto {

    private String username;
    private String password;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private Long adresseId;
    private Long lieuNaissanceId;


    // Getters
    public String getUsername() {
        return username;
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
    public Long getAdresseId() {
        return adresseId;
    }
    public Long getLieuNaissanceId() {
        return lieuNaissanceId;
    }

    //Setters

    public void setUsername(String username) {
        this.username = username;
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
    public void setAdresseId(Long adresseId) {
        this.adresseId = adresseId;
    }
    public void setLieuNaissanceId(Long lieuNaissanceId) {
        this.lieuNaissanceId = lieuNaissanceId;
    }
}
