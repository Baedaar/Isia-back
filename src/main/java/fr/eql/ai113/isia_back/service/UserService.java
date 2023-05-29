package fr.eql.ai113.isia_back.service;

import fr.eql.ai113.isia_back.entity.Document;
import fr.eql.ai113.isia_back.entity.Employe;
import fr.eql.ai113.isia_back.entity.User;
import fr.eql.ai113.isia_back.entity.dto.AdminDto;
import fr.eql.ai113.isia_back.entity.dto.AdresseDto;
import fr.eql.ai113.isia_back.entity.dto.EmployeDto;
import fr.eql.ai113.isia_back.entity.dto.LieuNaissanceDto;
import fr.eql.ai113.isia_back.repository.UserDao;
import fr.eql.ai113.isia_back.service.impl.exception.AccountExistsException;
import fr.eql.ai113.isia_back.service.impl.exception.DocumentException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Authentication authenticate(String username, String password) throws AuthenticationException;

    UserDetails save(EmployeDto employeDto,
                     AdresseDto adresseDto,
                     LieuNaissanceDto lieuNaissanceDto) throws AccountExistsException;
    UserDetails update(Employe employe);

    String generateJwtForUser(UserDetails user);

    UserDetails getUserFromJwt(String jwt);

    UserDetails saveAdmin(AdminDto adminDto) throws AccountExistsException;

    Optional<Document> getFile(Long fileId);

    List<Document> getFiles();

    List<User> findAllUsers();
}
