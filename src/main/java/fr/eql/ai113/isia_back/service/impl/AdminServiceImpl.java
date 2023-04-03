package fr.eql.ai113.isia_back.service.impl;

import fr.eql.ai113.isia_back.entity.Adresse;
import fr.eql.ai113.isia_back.entity.Document;
import fr.eql.ai113.isia_back.entity.Employe;
import fr.eql.ai113.isia_back.entity.LieuNaissance;
import fr.eql.ai113.isia_back.entity.dto.AdresseDto;
import fr.eql.ai113.isia_back.entity.dto.EmployeDto;
import fr.eql.ai113.isia_back.entity.dto.LieuNaissanceDto;
import fr.eql.ai113.isia_back.repository.AdresseDao;
import fr.eql.ai113.isia_back.repository.DocumentDao;
import fr.eql.ai113.isia_back.repository.EmployeDao;
import fr.eql.ai113.isia_back.repository.LieuNaissanceDao;
import fr.eql.ai113.isia_back.service.AdminService;
import fr.eql.ai113.isia_back.service.AdresseService;
import fr.eql.ai113.isia_back.service.LieuNaissanceService;
import fr.eql.ai113.isia_back.service.PasswordService;
import fr.eql.ai113.isia_back.service.impl.exception.DocumentException;
import fr.eql.ai113.isia_back.service.impl.exception.EmployeNotFoundException;
import fr.eql.ai113.isia_back.service.impl.exception.PasswordGenerationException;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AdminServiceImpl implements AdminService, AdresseService, LieuNaissanceService, PasswordService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);


    private EmployeDao employeDao;
    private LieuNaissanceDao lieuNaissanceDao;
    private AdresseDao adresseDao;

    @Autowired
    public void setEmployeDao(EmployeDao employeDao) {
        this.employeDao = employeDao;
    }

    @Autowired
    public void setLieuNaissanceDao(LieuNaissanceDao lieuNaissanceDao) {
        this.lieuNaissanceDao = lieuNaissanceDao;
    }

    @Autowired
    public void setAdresseDao(AdresseDao adresseDao) {
        this.adresseDao = adresseDao;
    }

    @Autowired
    private DocumentDao documentDao;


    @Override
    public Adresse creerAdresse(AdresseDto adresseDto) {
        Adresse adresse = new Adresse(
                adresseDto.getNumeroRue(),
                adresseDto.getRue(),
                adresseDto.getVille()
        );
        adresseDao.save(adresse);
        return adresse;
    }

    @Override
    public LieuNaissance creerLieuNaissance(LieuNaissanceDto lieuNaissanceDto) {
        LieuNaissance lieuNaissance = new LieuNaissance(
                lieuNaissanceDto.getVilleNaissance(),
                lieuNaissanceDto.getVilleNaissance()
        );
        lieuNaissanceDao.save(lieuNaissance);

        return lieuNaissance;
    }

    private String generateLogin(EmployeDto employe) {
        Random random = new Random();
        int upperBound = 99;

        return employe.getNom().substring(0, 2)
                + employe.getPrenom().substring(0, 2)
                + random.nextInt(upperBound);
    }

    @Override
    public String generateRandomPassword() throws PasswordGenerationException {

        CharacterRule alpha = new CharacterRule(EnglishCharacterData.Alphabetical);
        CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
        CharacterRule special = new CharacterRule(EnglishCharacterData.Special);

        PasswordGenerator passwordGenerator = new PasswordGenerator();

        return passwordGenerator.generatePassword(8, alpha, digits, special);
    }

    @Override
    public List<String> creerCompteEmploye(EmployeDto employeDto,
                                      AdresseDto adresseDto,
                                      LieuNaissanceDto lieuNaissanceDto) throws PasswordGenerationException {
        List<String> loginPassword = new ArrayList<>(2);

        Adresse adresse = new Adresse(
                adresseDto.getNumeroRue(),
                adresseDto.getRue(),
                adresseDto.getVille()
        );

        adresseDao.save(adresse);

        LieuNaissance naissance = new LieuNaissance(
                lieuNaissanceDto.getPaysNaissance(),
                lieuNaissanceDto.getPaysNaissance()
        );

        lieuNaissanceDao.save(naissance);

        Employe newEmploye = new Employe(
                generateLogin(employeDto),
                generateRandomPassword(),
                employeDto.getNom(),
                employeDto.getPrenom(),
                employeDto.getDateNaissance(),
                naissance,
                adresse
        );
        employeDao.save(newEmploye);
        loginPassword.add(newEmploye.getLogin());
        loginPassword.add(newEmploye.getPassword());

        return loginPassword;
    }

    @Override
    public Document enregistrerDocument(MultipartFile file, String docName, String docType) throws DocumentException {
        try {
            Document document = new Document(docName, docType, file.getBytes());
            return documentDao.save(document);
        } catch (IOException e) {
            throw new DocumentException("Un problème est survenu lors de l'upload", e);
        }
    }

    @Override
    public Optional<Document> getFile(Integer fileId) {
        return documentDao.findById(fileId);
    }

    @Override
    public List<Document> getFiles() {
        return documentDao.findAll();
    }


    @Override
    public String displayNewLogin(Employe employe) throws EmployeNotFoundException {
        String login;
        Optional<Employe> employeSearched = employeDao.findById(employe.getId());
        try {
            if (employeSearched.isPresent()) {
                employe = employeSearched.get();
                login = employe.getLogin();

                return login;
            }
        } catch (Exception e) {
            throw new EmployeNotFoundException("Cet employé n'existe pas", e);
        }
        return null;
    }

    @Override
    public String displayNewPassword(Employe employe) throws EmployeNotFoundException {
        String password;
        Optional<Employe> employeSearched = employeDao.findById(employe.getId());
        try {
            if (employeSearched.isPresent()) {
                employe = employeSearched.get();
                password = employe.getPassword();

                return password;
            }
        } catch (Exception e) {
            throw new EmployeNotFoundException("Cet employé n'existe pas", e);
        }
        return null;
    }
}
