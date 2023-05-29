package fr.eql.ai113.isia_back.repository;

import fr.eql.ai113.isia_back.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminDao extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
}
