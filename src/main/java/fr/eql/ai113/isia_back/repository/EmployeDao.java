package fr.eql.ai113.isia_back.repository;

import fr.eql.ai113.isia_back.entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeDao extends JpaRepository<Employe, Long> {

    Employe findByUsername(String login);
    Employe findEmployeByNomAndPrenom(String nom, String prenom);
}
