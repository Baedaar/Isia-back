package fr.eql.ai113.isia_back.repository;

import fr.eql.ai113.isia_back.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {

    Role findByName(String role);
}
