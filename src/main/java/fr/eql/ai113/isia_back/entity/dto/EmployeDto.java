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
}
