package fr.eql.ai113.isia_back.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name="document")
public class Document {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String docName;
    private String docType;

    @Nullable
    @ManyToOne
    private User visibleBy;

    @ManyToOne
    private User uploadedBy;


    @Lob
    private byte[] data;




    public Document() {}

    public Document(String docName, String docType, byte[] data, User visibleBy, User uploadedBy) {
        super();
        this.docName = docName;
        this.docType = docType;
        this.data = data;
        this.visibleBy = visibleBy;
        this.uploadedBy = uploadedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}