package fr.eql.ai113.isia_back;

import fr.eql.ai113.isia_back.entity.Adresse;
import fr.eql.ai113.isia_back.entity.Document;
import fr.eql.ai113.isia_back.entity.Employe;
import fr.eql.ai113.isia_back.entity.dto.AdresseDto;
import fr.eql.ai113.isia_back.entity.dto.EmployeDto;
import fr.eql.ai113.isia_back.repository.AdresseDao;
import fr.eql.ai113.isia_back.repository.DocumentDao;
import fr.eql.ai113.isia_back.repository.EmployeDao;
import fr.eql.ai113.isia_back.service.impl.EmployeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeServiceImplTest {

    @InjectMocks
    private EmployeServiceImpl employeService;

    @Mock
    private EmployeDao employeDao;

    @Mock
    private AdresseDao adresseDao;

    @Mock
    private DocumentDao documentDao;

    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup(){
        passwordEncoder = new BCryptPasswordEncoder();
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testVoirDocEmploye() {
        String username = "testUser";
        Employe employe = new Employe();
        List<Document> documents = new ArrayList<>();

        when(employeDao.findByUsername(username)).thenReturn(employe);
        when(documentDao.findAllByVisibleBy(employe)).thenReturn(documents);

        List<Document> result = employeService.voirDocEmploye(username);
        assertEquals(documents, result);
    }

    @Test
    public void testUpdate() throws AccountNotFoundException {
        String username = "testUser";
        EmployeDto employeDto = new EmployeDto();
        AdresseDto adresseDto = new AdresseDto();
        Employe employe = new Employe();
        Adresse adresse = new Adresse();

        String rawPassword = "a";
        employeDto.setNom("John");
        employeDto.setPrenom("Doe");
        String encodedPassword = passwordEncoder.encode(rawPassword);
        employeDto.setPassword(encodedPassword);

        adresseDto.setNumeroRue(123);
        adresseDto.setRue("Baker Street");
        adresseDto.setVille("London");

        when(employeDao.findByUsername(username)).thenReturn(employe);
        when(adresseDao.save(any(Adresse.class))).thenReturn(adresse);
        when(employeDao.save(any(Employe.class))).thenAnswer(invocation -> {
            Employe passedEmploye = invocation.getArgument(0);
            passedEmploye.setPassword(encodedPassword);
            return passedEmploye;
        });

        UserDetails result = employeService.update(username, employeDto, adresseDto);

        assertEquals("John", employe.getNom());
        assertEquals("Doe", employe.getPrenom());
        assertTrue(passwordEncoder.matches(rawPassword, employe.getPassword()));
    }

    @Test
    public void testEmployeInfo() {
        String username = "testUser";
        Employe employe = new Employe();

        when(employeDao.findByUsername(username)).thenReturn(employe);

        UserDetails result = employeService.employeInfo(username);
        assertEquals(employe, result);
    }
}
