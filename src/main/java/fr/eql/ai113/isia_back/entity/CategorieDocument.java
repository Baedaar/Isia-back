package fr.eql.ai113.isia_back.entity;

import javax.persistence.*;

import java.util.List;

@Entity
public class CategorieDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomCategorie;

    //@OneToMany(mappedBy = "categorieDocument")
    //private List<Document> documents;


    public CategorieDocument(String nomCategorie, List<Document> document) {
        this.nomCategorie = nomCategorie;
       // this.documents = document;
    }

    public CategorieDocument() {
        super();
    }


    // Getters
    public Long getId() {
        return id;
    }
    public String getNomCategorie() {
        return nomCategorie;
    }
   // public List<Document> getDocument() {
        // return documents;
   // }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }
    public void setDocument(List<Document> document) {
        //this.documents = document;
    }
}
