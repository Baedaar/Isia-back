package fr.eql.ai113.isia_back.service;

import fr.eql.ai113.isia_back.entity.Document;
import fr.eql.ai113.isia_back.service.impl.exception.DocumentException;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {

    Document enregistrerDocument(MultipartFile file, String docName, String admin, String employe) throws DocumentException;
    Document telechargerDoc(Long docId, String employeUsername) throws DocumentException;
}
