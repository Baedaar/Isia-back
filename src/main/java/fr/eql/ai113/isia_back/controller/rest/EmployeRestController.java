package fr.eql.ai113.isia_back.controller.rest;

import fr.eql.ai113.isia_back.complexe.request.UpdateEmployeReq;
import fr.eql.ai113.isia_back.entity.Document;
import fr.eql.ai113.isia_back.entity.dto.AdresseDto;
import fr.eql.ai113.isia_back.entity.dto.EmployeDto;
import fr.eql.ai113.isia_back.service.DocumentService;
import fr.eql.ai113.isia_back.service.EmployeService;
import fr.eql.ai113.isia_back.service.UserService;
import fr.eql.ai113.isia_back.service.impl.exception.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

/**
 * Contrôleur REST pour les actions liées à l'employé.
 * Ce contrôleur expose des API REST pour effectuer des opérations
 * telles que la mise à jour des informations de l'employé,
 * la visualisation des documents de l'employé et le téléchargement de documents.
 * Les API sont accessibles via l'URL de base "/employe".
 *
 * @author Rana Baedaar
 * @version 1.0
 */

@RestController
@RequestMapping("employe")
@CrossOrigin(origins = "${front.url}")
public class EmployeRestController {

    EmployeService employeService;
    DocumentService documentService;
    UserService userService;

    /**
     * API pour télécharger un document spécifique.
     *
     * @param docId L'ID du document à télécharger
     * @param employeUsername Le nom d'utilisateur de l'employé qui télécharge le document
     * @return Une ResponseEntity contenant le document téléchargé
     * @throws DocumentException Si une erreur se produit lors du téléchargement du document
     */
    @GetMapping("/download")
    public ResponseEntity<Resource> telechargerDoc(@RequestParam Long docId, @RequestParam String employeUsername ) throws DocumentException {
        Document document = documentService.telechargerDoc(docId,employeUsername);

        if (document == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ByteArrayResource resource = new ByteArrayResource(document.getData());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getDocName() + "\"")
                .body(resource);

    }


    /**
     * API pour obtenir une liste de tous les documents d'un employé spécifique.
     *
     * @param employeUsername Le nom d'utilisateur de l'employé pour lequel récupérer les documents
     * @return Une liste contenant tous les documents de l'employé spécifié
     */
    @GetMapping("/allDocuments")
    public List<Document> allDocuments(@RequestParam String employeUsername) {
        return employeService.voirDocEmploye(employeUsername);
    }


    /**
     * API pour mettre à jour les informations d'un employé.
     *
     * @param username Le nom d'utilisateur de l'employé à mettre à jour
     * @param req L'objet UpdateEmployeReq contenant les nouvelles informations de l'employé
     * @return Une ResponseEntity contenant les nouvelles informations de l'employé
     * @throws AccountNotFoundException Si l'employé à mettre à jour n'est pas trouvé
     */
    @PutMapping("/update")
    public ResponseEntity<UserDetails> update(@RequestParam String username, @RequestBody UpdateEmployeReq req) throws AccountNotFoundException {
        EmployeDto employeDto = req.getEmployeDto();
        AdresseDto adresseDto = req.getAdresseDto();
        UserDetails employe = employeService.update(username, employeDto, adresseDto);
        return ResponseEntity.ok(employe);
    }



    @Autowired
    public void setEmployeService(EmployeService employeService) {
        this.employeService = employeService;
    }
    @Autowired
    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
