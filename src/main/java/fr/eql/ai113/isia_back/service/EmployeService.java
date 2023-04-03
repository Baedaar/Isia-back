package fr.eql.ai113.isia_back.service;

import fr.eql.ai113.isia_back.entity.dto.AdresseDto;
import fr.eql.ai113.isia_back.entity.dto.EmployeDto;

public interface EmployeService {

    void changerInfoPerso(EmployeDto employeDto);
    void changerAdresse(EmployeDto employeDto, AdresseDto adresseDto);

}
