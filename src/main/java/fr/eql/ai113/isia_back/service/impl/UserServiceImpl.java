package fr.eql.ai113.isia_back.service.impl;

import fr.eql.ai113.isia_back.entity.*;
import fr.eql.ai113.isia_back.entity.dto.AdminDto;
import fr.eql.ai113.isia_back.entity.dto.AdresseDto;
import fr.eql.ai113.isia_back.entity.dto.EmployeDto;
import fr.eql.ai113.isia_back.entity.dto.LieuNaissanceDto;
import fr.eql.ai113.isia_back.repository.*;
import fr.eql.ai113.isia_back.service.UserService;
import fr.eql.ai113.isia_back.service.impl.exception.AccountExistsException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implémentation du service de gestion des utilisateurs.
 * Gère les opérations d'authentification, enregistrement, mise à jour et gestion de fichiers pour les utilisateurs.
 */

@Service
@Configuration
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger();

    /** Injecté par le setter */
    private EmployeDao employeDao;
    private AdresseDao adresseDao;
    private LieuNaissanceDao lieuNaissanceDao;
    private AdminDao adminDao;
    private RoleDao roleDao;
    private DocumentDao documentDao;
    private UserDao userDao;
    /** Injecté par le setter */
    private AuthenticationManager authenticationManager;

    private final String signingKey;

    public UserServiceImpl(@Value("${jwt.signing.key}") String signingKey) {
        this.signingKey = signingKey;
    }


    @Autowired
    public UserServiceImpl(@Value("${jwt.signing.key}") String signingKey,
                           AuthenticationManager authenticationManager) {
        this.signingKey = signingKey;
        this.authenticationManager = authenticationManager;
    }




    /**
     * Récupère un fichier spécifique par son ID.
     *
     * @param fileId L'ID du fichier à récupérer
     * @return Le fichier recherché, ou un objet vide si le fichier n'a pas été trouvé
     */
    @Override
    public Optional<Document> getFile(Long fileId) {
        return documentDao.findById(fileId);
    }


    /**
     * Récupère tous les fichiers.
     *
     * @return Une liste de tous les fichiers
     */
    @Override
    public List<Document> getFiles() {
        return documentDao.findAll();
    }


    /**
     * Récupère tous les utilisateurs.
     *
     * @return Une liste de tous les utilisateurs
     */
    @Override
    public List<User> findAllUsers() {

        return userDao.findAll();
    }


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Tente d'authentifier un utilisateur avec le nom d'utilisateur et le mot de passe fournis.
     *
     * @param username Le nom d'utilisateur à authentifier
     * @param password Le mot de passe à utiliser pour l'authentification
     * @return Les détails de l'utilisateur authentifié
     * @throws AuthenticationException Si l'authentification échoue
     */
    @Override
    public Authentication authenticate(String username, String password) throws AuthenticationException {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authentication);
    }




    private String generateUsername(EmployeDto employe) {
        Random random = new Random();
        int upperBound = 99;

        return employe.getNom().substring(0, 2)
                + employe.getPrenom().substring(0, 2)
                + random.nextInt(upperBound);
    }


    /**
     * Enregistre un nouvel employé dans le système.
     *
     * @param employeDto Les détails de l'employé à enregistrer
     * @param adresseDto Les détails de l'adresse de l'employé
     * @param lieuNaissanceDto Les détails du lieu de naissance de l'employé
     * @return Les détails de l'employé enregistré
     * @throws AccountExistsException Si un compte avec le même nom d'utilisateur existe déjà
     */
    @Override
    public UserDetails save(EmployeDto employeDto,
                            AdresseDto adresseDto,
                            LieuNaissanceDto lieuNaissanceDto) throws AccountExistsException {
        if (employeDao.findByUsername(employeDto.getUsername()) != null) {
            throw new AccountExistsException();
        }
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

        Employe employe = new Employe(
                generateUsername(employeDto),
                passwordEncoder().encode(employeDto.getPassword()),
                null,
                employeDto.getNom(),
                employeDto.getPrenom(),
                employeDto.getDateNaissance(),
                null,
                naissance,
                adresse,
                null
        );

        // Ajout des rôles
        List<Role> roles = new ArrayList<>();
        roles.add(roleDao.findByName("ROLE_USER")); // Ajout du rôle user
        employe.setRoles(roles);

        employeDao.save(employe);
        return employe;
    }


    /**
     * Met à jour les détails d'un employé existant.
     *
     * @param employe Les nouvelles informations de l'employé à mettre à jour
     * @return Les détails de l'employé mis à jour
     */
    @Override
    public UserDetails update(Employe employe) {
        return userDao.save(employe);
    }


    /**
     * Génère un JWT pour un utilisateur spécifique.
     *
     * @param user L'utilisateur pour lequel générer le JWT
     * @return Le JWT généré
     */
    @Override
    public String generateJwtForUser(UserDetails user) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 3600 * 1000);
        return Jwts.builder().setSubject(user.getUsername()).setIssuedAt(now).setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, signingKey).compact();
    }


    /**
     * Récupère les détails d'un utilisateur à partir d'un JWT.
     *
     * @param jwt Le JWT à partir duquel récupérer les détails de l'utilisateur
     * @return Les détails de l'utilisateur récupérés à partir du JWT
     */
    @Override
    public UserDetails getUserFromJwt(String jwt) {
        String username = getUsernameFromToken(jwt);
        return loadUserByUsername(username);
    }

    private String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }


    /**
     * Charge les détails d'un utilisateur par son nom d'utilisateur.
     *
     * @param username Le nom d'utilisateur de l'utilisateur à charger
     * @return Les détails de l'utilisateur chargé
     * @throws UsernameNotFoundException Si aucun utilisateur avec le nom d'utilisateur donné n'a été trouvé
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Le propriétaire n'a pas été trouvé.");
        }
        return user;
    }


    /**
     * Enregistre un nouvel administrateur dans le système.
     *
     * @param adminDto Les détails de l'administrateur à enregistrer
     * @return Les détails de l'administrateur enregistré
     * @throws AccountExistsException Si un compte avec le même nom d'utilisateur existe déjà
     */
    @Override
    public UserDetails saveAdmin(AdminDto adminDto) throws AccountExistsException {
        if (adminDao.findByUsername(adminDto.getUsername()) != null) {
            throw new AccountExistsException();
        }

        Collection<Role> roles = new ArrayList<>();
        roles.add(roleDao.findByName("ROLE_ADMIN")); // Ajout du rôle admin

        Admin admin = new Admin(
                adminDto.getUsername(),
                passwordEncoder().encode(adminDto.getPassword()),
                null,
                null
        );
        admin.setRoles(roles); // Définition des rôles pour l'admin

        adminDao.save(admin); // Enregistrement de l'admin

        return admin;
    }




    /// Setters ///
    @Autowired
    public void setAdresseDao(AdresseDao adresseDao) {
        this.adresseDao = adresseDao;
    }

    @Autowired
    public void setLieuNaissanceDao(LieuNaissanceDao lieuNaissanceDao) {
        this.lieuNaissanceDao = lieuNaissanceDao;
    }

    @Autowired
    public void setEmployeDao(EmployeDao employeDao) {
        this.employeDao = employeDao;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        logger.info("AuthenticationManager has been injected: " + (authenticationManager != null));
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }
    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
    @Autowired
    public void setDocumentDao(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
