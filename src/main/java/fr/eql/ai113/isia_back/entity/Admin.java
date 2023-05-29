package fr.eql.ai113.isia_back.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin  extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToMany
    private List<Document> documentsUploaded;

    public Admin(String username, String password, Collection<Role> roles, List<Document> documents) {
        super(username, password, roles);
        this.documentsUploaded = documents;
    }

    public Admin() {
    }

    public List<Document> getDocumentsUploaded() {
        return documentsUploaded;
    }

    public void setDocumentsUploaded(List<Document> documentsUploaded) {
        this.documentsUploaded = documentsUploaded;
    }
}