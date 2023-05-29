package fr.eql.ai113.isia_back.entity.dto;

public class AdresseDto {

    private Integer numeroRue;
    private String rue;
    private String ville;
    private Long employe_Id;

    //Getters
    public Integer getNumeroRue() {
        return numeroRue;
    }
    public String getRue() {
        return rue;
    }
    public String getVille() {
        return ville;
    }
    public Long getClient_Id() {
        return employe_Id;
    }

    //Setters
    public void setNumeroRue(Integer numeroRue) {
        this.numeroRue = numeroRue;
    }
    public void setRue(String rue) {
        this.rue = rue;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }
    public void setEmploye_Id(Long employe_Id) {
        this.employe_Id = employe_Id;
    }
}
