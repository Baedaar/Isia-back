package fr.eql.ai113.isia_back.service.impl;

import fr.eql.ai113.isia_back.entity.Adresse;
import fr.eql.ai113.isia_back.entity.dto.AdresseDto;
import fr.eql.ai113.isia_back.entity.dto.EmployeDto;
import fr.eql.ai113.isia_back.repository.AdresseDao;
import fr.eql.ai113.isia_back.repository.EmployeDao;
import fr.eql.ai113.isia_back.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeServiceImpl implements EmployeService {

    private EmployeDao employeDao;
    private AdresseDao adresseDao;

    @Autowired
    public void setEmployeDao(EmployeDao employeDao) {this.employeDao = employeDao;}
    @Autowired
    public void setAdresseDao(AdresseDao adresseDao) {this.adresseDao = adresseDao;}

    @Override
    public void changerInfoPerso(EmployeDto employeDto) {

    }

    @Override
    public void changerAdresse(EmployeDto employeDto, AdresseDto adresseDto) {
        Adresse modifiedAdresse = new Adresse();
        Optional<Adresse> adresse = adresseDao.findById(employeDto.getAdresseId());
        if (adresse.isPresent()) {
            modifiedAdresse = adresse.get();
        }
        modifiedAdresse.setNumeroRue(modifiedAdresse.getNumeroRue());
        modifiedAdresse.setRue(modifiedAdresse.getRue());
        modifiedAdresse.setVille(modifiedAdresse.getVille());

    }
}
