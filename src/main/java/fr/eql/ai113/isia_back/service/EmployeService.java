package fr.eql.ai113.isia_back.service;

import fr.eql.ai113.isia_back.entity.Document;
import fr.eql.ai113.isia_back.entity.dto.AdresseDto;
import fr.eql.ai113.isia_back.entity.dto.EmployeDto;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface EmployeService {

    void changerInfoPerso(EmployeDto employeDto);
    void changerAdresse(EmployeDto employeDto, AdresseDto adresseDto);
    List<Document> voirDocEmploye(String employeUsername);
    UserDetails update(String username,
                       EmployeDto employeDto,
                       AdresseDto adresseDto) throws AccountNotFoundException;
    UserDetails employeInfo(String username);

}
