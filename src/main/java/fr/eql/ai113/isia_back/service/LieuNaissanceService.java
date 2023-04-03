package fr.eql.ai113.isia_back.service;

import fr.eql.ai113.isia_back.entity.LieuNaissance;
import fr.eql.ai113.isia_back.entity.dto.LieuNaissanceDto;

public interface LieuNaissanceService {

    LieuNaissance creerLieuNaissance(LieuNaissanceDto lieuNaissanceDto);

}
