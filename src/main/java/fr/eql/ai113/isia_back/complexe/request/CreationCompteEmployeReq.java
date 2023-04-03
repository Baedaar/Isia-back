package fr.eql.ai113.isia_back.complexe.request;

import fr.eql.ai113.isia_back.entity.dto.AdresseDto;
import fr.eql.ai113.isia_back.entity.dto.EmployeDto;
import fr.eql.ai113.isia_back.entity.dto.LieuNaissanceDto;

public class CreationCompteEmployeReq {
    private EmployeDto employeDto;
    private AdresseDto adresseDto;
    private LieuNaissanceDto lieuNaissanceDto;


    // Getters
    public EmployeDto getEmployeDto() {
        return employeDto;
    }
    public AdresseDto getAdresseDto() {
        return adresseDto;
    }
    public LieuNaissanceDto getLieuNaissanceDto() {
        return lieuNaissanceDto;
    }

    //Setters
    public void setEmployeDto(EmployeDto employeDto) {
        this.employeDto = employeDto;
    }
    public void setAdresseDto(AdresseDto adresseDto) {
        this.adresseDto = adresseDto;
    }
    public void setLieuNaissanceDto(LieuNaissanceDto lieuNaissanceDto) {
        this.lieuNaissanceDto = lieuNaissanceDto;
    }
}
