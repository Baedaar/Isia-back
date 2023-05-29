package fr.eql.ai113.isia_back.service.impl;

import fr.eql.ai113.isia_back.entity.Adresse;
import fr.eql.ai113.isia_back.entity.Document;
import fr.eql.ai113.isia_back.entity.Employe;
import fr.eql.ai113.isia_back.entity.dto.AdresseDto;
import fr.eql.ai113.isia_back.entity.dto.EmployeDto;
import fr.eql.ai113.isia_back.repository.AdresseDao;
import fr.eql.ai113.isia_back.repository.DocumentDao;
import fr.eql.ai113.isia_back.repository.EmployeDao;
import fr.eql.ai113.isia_back.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;


/**
 * Implémentation du service de gestion des employés.
 * Gère les opérations de mise à jour des informations personnelles, changement d'adresse, visualisation de documents et mise à jour des informations d'un employé.
 */
@Service
public class EmployeServiceImpl implements EmployeService {

    private EmployeDao employeDao;
    private AdresseDao adresseDao;
    private DocumentDao documentDao;


    /**
     * Fournit un encodeur de mot de passe pour l'authentification.
     *
     * @return Un nouvel encodeur de mot de passe BCrypt
     */
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Modifie les informations personnelles d'un employé.
     *
     * @param employeDto Les nouvelles informations personnelles de l'employé
     */
    @Override
    public void changerInfoPerso(EmployeDto employeDto) {

    }

    /**
     * Modifie l'adresse d'un employé.
     *
     * @param employeDto Les nouvelles informations de l'employé, y compris l'ID de la nouvelle adresse
     * @param adresseDto Les nouvelles informations d'adresse
     */
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


    /**
     * Récupère les documents visibles par un employé spécifique.
     *
     * @param employeUsername Le nom d'utilisateur de l'employé dont on veut voir les documents
     * @return Une liste de documents visibles par l'employé
     */
    @Override
    public List<Document> voirDocEmploye(String employeUsername) {
        return documentDao.findAllByVisibleBy(employeDao.findByUsername(employeUsername));
    }

    /**
     * Met à jour les informations d'un employé, y compris son adresse.
     *
     * @param username Le nom d'utilisateur de l'employé à mettre à jour
     * @param employeDto Les nouvelles informations de l'employé
     * @param adresseDto Les nouvelles informations d'adresse
     * @return Les détails de l'employé mis à jour
     * @throws AccountNotFoundException Si aucun employé avec le nom d'utilisateur donné n'a été trouvé
     */
    @Override
    public UserDetails update(String username,
                              EmployeDto employeDto,
                              AdresseDto adresseDto) throws AccountNotFoundException {
        Employe employe = employeDao.findByUsername(username);

        if (employe == null) {
            throw new AccountNotFoundException();
        }

        // Mise à jour de l'adresse
        Adresse adresse = employe.getAdresse();

        if (adresse == null) {
            adresse = new Adresse();
            employe.setAdresse(adresse);
        }

        if (adresseDto.getNumeroRue() != null) {
            adresse.setNumeroRue(adresseDto.getNumeroRue());
        }
        if (adresseDto.getRue() != null) {
            adresse.setRue(adresseDto.getRue());
        }
        if (adresseDto.getVille() != null) {
            adresse.setVille(adresseDto.getVille());
        }

        adresseDao.save(adresse);

        // Mise à jour de l'employé
        if (employeDto.getPassword() != null) {
            employe.setPassword(passwordEncoder().encode(employeDto.getPassword()));
        }
        if (employeDto.getNom() != null) {
            employe.setNom(employeDto.getNom());
        }
        if (employeDto.getPrenom() != null) {
            employe.setPrenom(employeDto.getPrenom());
        }

        employeDao.save(employe);

        return employe;
    }


    /**
     * Récupère les informations d'un employé.
     *
     * @param username Le nom d'utilisateur de l'employé dont on veut obtenir les informations
     * @return Les détails de l'employé
     */
    @Override
    public UserDetails employeInfo(String username) {
        return employeDao.findByUsername(username);
    }


    @Autowired
    public void setEmployeDao(EmployeDao employeDao) {this.employeDao = employeDao;}
    @Autowired
    public void setAdresseDao(AdresseDao adresseDao) {this.adresseDao = adresseDao;}
    @Autowired
    public void setDocumentDao(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }
}
