package fr.eql.ai113.isia_back.service;

import fr.eql.ai113.isia_back.entity.Adresse;
import fr.eql.ai113.isia_back.entity.dto.AdresseDto;

public interface AdresseService {

    Adresse creerAdresse(AdresseDto adresseDto);

}
