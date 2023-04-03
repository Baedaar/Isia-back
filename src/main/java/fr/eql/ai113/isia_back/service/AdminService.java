package fr.eql.ai113.isia_back.service;

import fr.eql.ai113.isia_back.entity.Document;
import fr.eql.ai113.isia_back.entity.Employe;
import fr.eql.ai113.isia_back.entity.dto.AdresseDto;
import fr.eql.ai113.isia_back.entity.dto.EmployeDto;
import fr.eql.ai113.isia_back.entity.dto.LieuNaissanceDto;
import fr.eql.ai113.isia_back.service.impl.exception.DocumentException;
import fr.eql.ai113.isia_back.service.impl.exception.EmployeNotFoundException;
import fr.eql.ai113.isia_back.service.impl.exception.PasswordGenerationException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    List<String> creerCompteEmploye(EmployeDto employeDto, AdresseDto adresseDto, LieuNaissanceDto lieuNaissanceDto) throws PasswordGenerationException;
    Document enregistrerDocument(MultipartFile file, String docName, String docType) throws DocumentException;
    Optional<Document> getFile(Integer fileId);
    List<Document> getFiles();
    String displayNewLogin(Employe employe) throws EmployeNotFoundException;
    String displayNewPassword(Employe employe) throws EmployeNotFoundException;

}

