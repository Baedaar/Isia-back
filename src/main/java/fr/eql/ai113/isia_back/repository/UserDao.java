package fr.eql.ai113.isia_back.repository;

import fr.eql.ai113.isia_back.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


public interface UserDao extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);

    @Override
    List<User> findAll();
}
