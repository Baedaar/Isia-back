package fr.eql.ai113.isia_back.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Login;
    private String password;


    public Admin(String login, String password) {
        Login = login;
        this.password = password;
    }

    public Admin() {
    }

    // Getters
    public Long getId() {
        return id;
    }
    public String getLogin() {
        return Login;
    }
    public String getPassword() {
        return password;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setLogin(String login) {
        Login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
