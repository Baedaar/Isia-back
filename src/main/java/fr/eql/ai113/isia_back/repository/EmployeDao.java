package fr.eql.ai113.isia_back.repository;

import fr.eql.ai113.isia_back.entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EmployeDao extends JpaRepository<Employe, Long> {

    Employe findByUsername(String login);
    Optional<Employe> findById(Long id);
    Employe findEmployeByNomAndPrenom(String nom, String prenom);
}
