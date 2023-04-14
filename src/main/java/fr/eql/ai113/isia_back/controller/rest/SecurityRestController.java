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

@RestController
@RequestMapping("security")
@CrossOrigin(origins = "${front.url}")
public class SecurityRestController {

    private static final Logger logger = LogManager.getLogger();

    /** Injecté par le setter */
    UserService userService;

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

    @PostMapping("/authorizeAdmin")
    public ResponseEntity<AuthResponse> authorizeAdmin(@RequestBody AuthRequest requestDto) throws UnauthorizedException {
        Authentication authentication;
        try {
            authentication = userService.authenticateAdmin(requestDto.getUsername(), requestDto.getPassword());
            logger.info("111111111111");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("22222222222");
            UserDetails admin = (UserDetails) authentication.getPrincipal();
            logger.info("333333333333");
            String token = userService.generateJwtForUser(admin);
            logger.info("444444444444");
            return ResponseEntity.ok(new AuthResponse(admin, token));
        } catch (AuthenticationException e) {
            throw new UnauthorizedException("Erreur Controlleur admin", e);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody CreationCompteEmployeReq req) throws AccountExistsException {
        AdresseDto adresseDto = req.getAdresseDto();
        LieuNaissanceDto lieuNaissanceDto = req.getLieuNaissanceDto();
        EmployeDto employeDto = req.getEmployeDto();
        UserDetails employe = userService.save(employeDto, adresseDto,lieuNaissanceDto);
        String token  = userService.generateJwtForUser(employe);
        return ResponseEntity.ok(new AuthResponse(employe, token));
    }

    @PostMapping("/registerAdmin")
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
