package fr.eql.ai113.isia_back.controller.rest;

import fr.eql.ai113.isia_back.complexe.request.CreationCompteEmployeReq;
import fr.eql.ai113.isia_back.entity.Document;
import fr.eql.ai113.isia_back.entity.User;
import fr.eql.ai113.isia_back.entity.dto.*;
import fr.eql.ai113.isia_back.service.AdminService;
import fr.eql.ai113.isia_back.service.DocumentService;
import fr.eql.ai113.isia_back.service.UserService;
import fr.eql.ai113.isia_back.service.impl.exception.AccountExistsException;
import fr.eql.ai113.isia_back.service.impl.exception.DocumentException;
import fr.eql.ai113.isia_back.service.impl.exception.EmployeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.json.JSONObject;


import java.time.LocalDate;
import java.util.List;

/**
 * Classe de contrôleur REST pour la gestion des administrateurs dans l'application.
 * Cette classe gère les différentes routes HTTP relatives aux opérations de l'administrateur.
 * Elle utilise Spring MVC et est automatiquement instanciée par Spring grâce à l'annotation @RestController.
 *
 * @RestController Indique à Spring que cette classe est un contrôleur REST.
 * @RequestMapping("admin") Définit le chemin de base pour toutes les méthodes du contrôleur.
 * @CrossOrigin(origins = "${front.url}") Permet les requêtes CORS depuis l'URL spécifiée.
 *
 * La classe déclare trois services qui seront injectés par Spring :
 *  - DocumentService
 *  - UserService
 *  - AdminService
 *
 * Elle expose les endpoints suivants :
 *
 * GET /admin/ : récupère la liste des documents disponibles pour l'administrateur.
 * POST /admin/uploadFile : permet à l'administrateur d'uploader un fichier/document.
 * POST /admin/register : crée un nouvel employé en enregistrant ses informations dans la base de données et en générant un JWT pour lui.
 * POST /admin/registerAdmin : crée un nouvel administrateur en enregistrant ses informations dans la base de données et en générant un JWT pour lui.
 * GET /admin/allUsers : récupère la liste de tous les utilisateurs enregistrés dans la base de données.
 * POST /admin/deleteEmployee : supprime un employé.
 *
 * Les méthodes setUserService et setDocumentService sont utilisées pour l'injection des dépendances par Spring.
 *
 * @author Rana Baedaar
 * @version 1.0
 */

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "${front.url}")
public class AdminRestController {

    /** Injecté par le setter */
    DocumentService documentService;
    UserService userService;
    AdminService adminService;



    /**
     * API pour récupérer une liste de tous les documents.
     *
     * @param model Le modèle à utiliser pour l'envoi des attributs à la vue
     * @return La vue à afficher, en l'occurrence "doc"
     */
    @GetMapping("/")
    public String get(Model model) {
        List<Document> docs = adminService.getFiles();
        model.addAttribute("docs", docs);
        return "doc";
    }


    /**
     * API pour enregistrer plusieurs fichiers.
     *
     * @param file Le fichier à télécharger
     * @param docName Le nom du document à télécharger
     * @param adminUsername Le nom d'utilisateur de l'administrateur qui télécharge le document
     * @param employeUsername Le nom d'utilisateur de l'employé qui télécharge le document
     * @return Une réponse indiquant le statut de l'opération
     * @throws DocumentException Si une erreur se produit lors de l'enregistrement du document
     */
    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadMultipleFiles(@RequestParam("file") MultipartFile file, @RequestParam("docName") String docName,
                                                      @RequestParam("adminUsername") String adminUsername, @RequestParam("employeUsername") String employeUsername) throws DocumentException {
        documentService.enregistrerDocument(file, docName, adminUsername, employeUsername);

        JSONObject responseJson = new JSONObject();
        responseJson.put("redirectUrl", "/");
        return ResponseEntity.ok(responseJson.toString());
    }

    /**
     * API pour enregistrer un nouvel employé.
     *
     * @param req L'objet CreationCompteEmployeReq contenant les informations de l'employé à enregistrer
     * @return Une réponse contenant les détails de l'employé enregistré et le token JWT
     * @throws AccountExistsException Si un compte avec le même nom d'utilisateur existe déjà
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody CreationCompteEmployeReq req) throws AccountExistsException {
        AdresseDto adresseDto = req.getAdresseDto();
        LieuNaissanceDto lieuNaissanceDto = req.getLieuNaissanceDto();
        EmployeDto employeDto = req.getEmployeDto();
        UserDetails employe = userService.save(employeDto, adresseDto,lieuNaissanceDto);
        String token  = userService.generateJwtForUser(employe);
        return ResponseEntity.ok(new AuthResponse(employe, token));
    }

    /**
     * API pour enregistrer un nouvel administrateur.
     *
     * @param adminDto L'objet AdminDto contenant les informations de l'administrateur à enregistrer
     * @return Une réponse contenant les détails de l'administrateur enregistré et le token JWT
     * @throws AccountExistsException Si un compte avec le même nom d'utilisateur existe déjà
     */
    @PostMapping("/registerAdmin")
    public ResponseEntity<AuthResponse> registerAdmin(@RequestBody AdminDto adminDto) throws AccountExistsException {
        UserDetails admin = userService.saveAdmin(adminDto);
        String token = userService.generateJwtForUser(admin);
        return ResponseEntity.ok(new AuthResponse(admin, token));
    }

    /**
     * API pour obtenir une liste de tous les utilisateurs.
     *
     * @return Une liste contenant tous les utilisateurs
     */
    @GetMapping("/allUsers")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }


    /**
     * API pour supprimer un employé.
     *
     * @param employeDto L'objet EmployeDto contenant les informations de l'employé à supprimer
     * @return La date à laquelle l'employé a été supprimé
     * @throws EmployeNotFoundException Si l'employé à supprimer n'est pas trouvé
     */
    @PostMapping("/deleteEmployee")
    @CrossOrigin(origins = "http://localhost:3000")
    public LocalDate deleteEmployee(@RequestBody EmployeDto employeDto) throws EmployeNotFoundException {
        return adminService.deleteEmploye(employeDto);
    }

    /// Setters ///
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

}