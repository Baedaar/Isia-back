package fr.eql.ai113.isia_back.service.impl;

import fr.eql.ai113.isia_back.entity.Admin;
import fr.eql.ai113.isia_back.entity.Document;
import fr.eql.ai113.isia_back.entity.Employe;
import fr.eql.ai113.isia_back.repository.AdminDao;
import fr.eql.ai113.isia_back.repository.DocumentDao;
import fr.eql.ai113.isia_back.repository.EmployeDao;
import fr.eql.ai113.isia_back.repository.UserDao;
import fr.eql.ai113.isia_back.service.DocumentService;
import fr.eql.ai113.isia_back.service.impl.exception.DocumentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Implémentation du service de gestion des documents.
 * Gère les opérations d'enregistrement et de téléchargement des documents.
 */

@Service
public class DocumentServiceImpl implements DocumentService {

    private static final Logger logger = LogManager.getLogger();

    private DocumentDao documentDao;
    private AdminDao adminDao;
    private EmployeDao employeDao;
    private UserDao userDao;


    /**
     * Enregistre un nouveau document en base de données.
     * Le document est associé à un administrateur et un employé spécifiques.
     *
     * @param file Le fichier à enregistrer
     * @param docName Le nom du document
     * @param adminUsername Le nom d'utilisateur de l'administrateur qui enregistre le document
     * @param employeUsername Le nom d'utilisateur de l'employé à qui le document est associé
     * @return Le document enregistré
     * @throws DocumentException Si une erreur se produit lors de l'enregistrement du document
     */
    @Override
    @Transactional
    public Document enregistrerDocument(MultipartFile file, String docName, String adminUsername, String employeUsername) throws DocumentException {
        Admin admin = adminDao.findByUsername(adminUsername);
        Employe employe = employeDao.findByUsername(employeUsername);
        try {
            Tika tika = new Tika();
            String docType = tika.detect(file.getBytes());
            logger.info("********** doc type tika : " + docType);
            Document document = new Document(docName, docType, file.getBytes(), employe, admin);
            admin.getDocumentsUploaded().add(document);
            employe.getEmployesDoc().add(document);

            return documentDao.save(document);
        } catch (IOException e) {
            throw new DocumentException("Un problème est survenu lors de l'upload", e);
        }
    }

    /**
     * Permet à un employé de télécharger un document.
     *
     * @param docId L'ID du document à télécharger
     * @param employeUsername Le nom d'utilisateur de l'employé qui télécharge le document
     * @return Le document téléchargé
     * @throws DocumentException Si une erreur se produit lors du téléchargement du document
     */
    @Override
    public Document telechargerDoc(Long docId, String employeUsername) throws DocumentException {
        UserDetails employe = userDao.findByUsername(employeUsername);

            return documentDao.findByVisibleByAndId(employe, docId);
    }

    @Autowired
    public void setDocumentDao(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }

    @Autowired
    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Autowired
    public void setEmployeDao(EmployeDao employeDao) {
        this.employeDao = employeDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
