package fr.eql.ai113.isia_back.repository;

import fr.eql.ai113.isia_back.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentDao extends JpaRepository<Document, Integer> {
}
