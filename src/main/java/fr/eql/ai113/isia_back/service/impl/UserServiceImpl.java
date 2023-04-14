package fr.eql.ai113.isia_back.service.impl;

import fr.eql.ai113.isia_back.entity.Admin;
import fr.eql.ai113.isia_back.entity.Adresse;
import fr.eql.ai113.isia_back.entity.Employe;
import fr.eql.ai113.isia_back.entity.LieuNaissance;
import fr.eql.ai113.isia_back.entity.dto.AdminDto;
import fr.eql.ai113.isia_back.entity.dto.AdresseDto;
import fr.eql.ai113.isia_back.entity.dto.EmployeDto;
import fr.eql.ai113.isia_back.entity.dto.LieuNaissanceDto;
import fr.eql.ai113.isia_back.repository.AdminDao;
import fr.eql.ai113.isia_back.repository.AdresseDao;
import fr.eql.ai113.isia_back.repository.EmployeDao;
import fr.eql.ai113.isia_back.repository.LieuNaissanceDao;
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

import java.util.Date;
import java.util.Random;

@Service
@Configuration
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger();

    /** Injecté par le setter */
    private EmployeDao employeDao;
    private AdresseDao adresseDao;
    private LieuNaissanceDao lieuNaissanceDao;
    private AdminDao adminDao;
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


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(String username, String password) throws AuthenticationException {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authentication);
    }

    @Override
    public Authentication authenticateAdmin(String username, String password) throws AuthenticationException {
        logger.info("*********** Dans la methode authenticateAdmin avant instanciation");
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        logger.info("*********** Dans la methode authenticateAdmin avant return");
        return authenticationManager.authenticate(authentication);
    }

    private String generateUsername(EmployeDto employe) {
        Random random = new Random();
        int upperBound = 99;

        return employe.getNom().substring(0, 2)
                + employe.getPrenom().substring(0, 2)
                + random.nextInt(upperBound);
    }

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
                employeDto.getNom(),
                employeDto.getPrenom(),
                employeDto.getDateNaissance(),
                naissance,
                adresse
        );
        employeDao.save(employe);
        return employe;
    }


    @Override
    public String generateJwtForUser(UserDetails user) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 3600 * 1000);
        return Jwts.builder().setSubject(user.getUsername()).setIssuedAt(now).setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, signingKey).compact();
    }

    @Override
    public UserDetails getUserFromJwt(String jwt) {
        String username = getUsernameFromToken(jwt);
        return loadUserByUsername(username);
    }

    private String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employe employe = employeDao.findByUsername(username);
        if (employe == null) {
            throw new UsernameNotFoundException("Le propriétaire n'a pas été trouvé.");
        }
        return employe;
    }

    @Override
    public UserDetails saveAdmin(AdminDto adminDto) throws AccountExistsException {
        if (adminDao.findByUsername(adminDto.getUsername()) != null) {
            throw new AccountExistsException();
        }
        Admin admin = new Admin(
                adminDto.getUsername(),
                passwordEncoder().encode(adminDto.getPassword())
        );
        adminDao.save(admin);


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
}
