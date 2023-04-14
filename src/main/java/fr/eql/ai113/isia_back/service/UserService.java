package fr.eql.ai113.isia_back.service;

import fr.eql.ai113.isia_back.entity.dto.AdminDto;
import fr.eql.ai113.isia_back.entity.dto.AdresseDto;
import fr.eql.ai113.isia_back.entity.dto.EmployeDto;
import fr.eql.ai113.isia_back.entity.dto.LieuNaissanceDto;
import fr.eql.ai113.isia_back.service.impl.exception.AccountExistsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Authentication authenticate(String username, String password) throws AuthenticationException;
    Authentication authenticateAdmin(String username, String password) throws AuthenticationException;
    UserDetails save(EmployeDto employeDto,
                     AdresseDto adresseDto,
                     LieuNaissanceDto lieuNaissanceDto) throws AccountExistsException;
    String generateJwtForUser(UserDetails user);
    UserDetails getUserFromJwt(String jwt);
    UserDetails saveAdmin(AdminDto adminDto) throws AccountExistsException;
}
