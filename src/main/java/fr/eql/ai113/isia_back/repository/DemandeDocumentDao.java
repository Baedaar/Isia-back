package fr.eql.ai113.isia_back.repository;

import fr.eql.ai113.isia_back.entity.Demandes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeDocumentDao extends JpaRepository<Demandes, Long> {

}
