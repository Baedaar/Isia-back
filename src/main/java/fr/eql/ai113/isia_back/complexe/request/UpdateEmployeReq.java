package fr.eql.ai113.isia_back.complexe.request;

import fr.eql.ai113.isia_back.entity.dto.AdresseDto;
import fr.eql.ai113.isia_back.entity.dto.EmployeDto;

/**
 * Cette classe permet de recupérer plusieurs objets, elle a été créé pour
 * faciliter la création des API REST.
 */
public class UpdateEmployeReq {
    private EmployeDto employeDto;
    private AdresseDto adresseDto;



    public EmployeDto getEmployeDto() {
        return employeDto;
    }

    public void setEmployeDto(EmployeDto employeDto) {
        this.employeDto = employeDto;
    }

    public AdresseDto getAdresseDto() {
        return adresseDto;
    }

    public void setAdresseDto(AdresseDto adresseDto) {
        this.adresseDto = adresseDto;
    }
}
