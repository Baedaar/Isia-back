package fr.eql.ai113.isia_back.repository;

import fr.eql.ai113.isia_back.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


public interface DocumentDao extends JpaRepository<Document, Long> {
    Document findByVisibleByAndId(UserDetails employe, Long docId);
    List<Document> findAllByVisibleBy(UserDetails employe);

}
