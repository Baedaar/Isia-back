package fr.eql.ai113.isia_back.controller.rest;


import fr.eql.ai113.isia_back.complexe.request.CreationCompteEmployeReq;
import fr.eql.ai113.isia_back.entity.dto.*;
import fr.eql.ai113.isia_back.service.UserService;
import fr.eql.ai113.isia_back.service.impl.exception.AccountExistsException;
import fr.eql.ai113.isia_back.service.impl.exception.UnauthorizedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour les actions liées à la sécurité et à l'authentification.
 * Ce contrôleur expose des API REST pour effectuer des opérations telles que l'authentification de l'utilisateur,
 * l'enregistrement d'un employé et d'un administrateur.
 * Les API sont accessibles via l'URL de base "/security".
 *
 * @author Rana Baedaar
 * @version 1.0
 */

@RestController
@RequestMapping("security")
@CrossOrigin(origins = "${front.url}")
public class SecurityRestController {

    private static final Logger logger = LogManager.getLogger();

    /** Injecté par le setter */
    UserService userService;

    /**
     * API pour authentifier un utilisateur.
     *
     * @param requestDto L'objet AuthRequest contenant les informations de connexion de l'utilisateur
     * @return Une réponse contenant les détails de l'utilisateur authentifié et le token JWT
     * @throws UnauthorizedException en cas d'échec de l'authentification
     */
    @PostMapping("/authorize")
    public ResponseEntity<AuthResponse> authorize(@RequestBody AuthRequest requestDto) throws UnauthorizedException {
        Authentication authentication;
        try {
            authentication = userService.authenticate(requestDto.getUsername(), requestDto.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails employe = (UserDetails) authentication.getPrincipal();
            String token = userService.generateJwtForUser(employe);
            return ResponseEntity.ok(new AuthResponse(employe, token));
        } catch (AuthenticationException e) {
            throw new UnauthorizedException("Erreur controlleur", e);
        }
    }


    /**
     * API pour authentifier un administrateur.
     *
     * @param requestDto L'objet AuthRequest contenant les informations de connexion de l'administrateur
     * @return Une réponse contenant les détails de l'administrateur authentifié et le token JWT
     * @throws UnauthorizedException en cas d'échec de l'authentification
     */
    @PostMapping("/authorizeAdmin")
    public ResponseEntity<AuthResponse> authorizeAdmin(@RequestBody AuthRequest requestDto) throws UnauthorizedException {
        Authentication authentication;
        try {
            authentication = userService.authenticate(requestDto.getUsername(), requestDto.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails admin = (UserDetails) authentication.getPrincipal();
            String token = userService.generateJwtForUser(admin);
            return ResponseEntity.ok(new AuthResponse(admin, token));
        } catch (AuthenticationException e) {
            throw new UnauthorizedException("Erreur Controlleur admin", e);
        }
    }

    /**
     * API pour enregistrer un nouvel employé.
     *
     * @param req L'objet CreationCompteEmployeReq contenant les informations de l'employé à enregistrer
     * @return Une réponse contenant les détails de l'employé enregistré et le token JWT
     * @throws AccountExistsException si un compte avec le même nom d'utilisateur existe déjà
     */
    @PostMapping("admin/register")
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
     * @throws AccountExistsException si un compte avec le même nom d'utilisateur existe déjà
     */
    @PostMapping("admin/registerAdmin")
    public ResponseEntity<AuthResponse> registerAdmin(@RequestBody AdminDto adminDto) throws AccountExistsException {
        UserDetails admin = userService.saveAdmin(adminDto);
        String token = userService.generateJwtForUser(admin);
        return ResponseEntity.ok(new AuthResponse(admin, token));
    }

    /// Setters ///
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
