package fr.eql.ai113.isia_back.controller.rest;

import fr.eql.ai113.isia_back.complexe.request.CreationCompteEmployeReq;
import fr.eql.ai113.isia_back.entity.Document;
import fr.eql.ai113.isia_back.entity.dto.AdresseDto;
import fr.eql.ai113.isia_back.entity.dto.EmployeDto;
import fr.eql.ai113.isia_back.entity.dto.LieuNaissanceDto;
import fr.eql.ai113.isia_back.service.AdminService;
import fr.eql.ai113.isia_back.service.impl.exception.DocumentException;
import fr.eql.ai113.isia_back.service.impl.exception.PasswordGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.json.JSONObject;


import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AdminRestController {

    private AdminService adminService;


    @GetMapping("/")
    public String get(Model model) {
        List<Document> docs = adminService.getFiles();
        model.addAttribute("docs", docs);
        return "doc";
    }

    @PostMapping("/admin/uploadFile")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> uploadMultipleFiles(@RequestParam("file") MultipartFile[] files, @RequestParam("docName") String docName, @RequestParam("docType") String docType) throws DocumentException {
        for (MultipartFile file : files) {
            adminService.enregistrerDocument(file, docName, docType);
        }
        JSONObject responseJson = new JSONObject();
        responseJson.put("redirectUrl", "/");
        return ResponseEntity.ok(responseJson.toString());
    }

    @PostMapping("/admin/saveEmployee")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<String> createEmployee(
            @RequestBody CreationCompteEmployeReq req) throws PasswordGenerationException {

        AdresseDto adresseDto = req.getAdresseDto();
        LieuNaissanceDto lieuNaissanceDto = req.getLieuNaissanceDto();
        EmployeDto employeDto = req.getEmployeDto();

        return adminService.creerCompteEmploye(employeDto, adresseDto,lieuNaissanceDto);
    }











    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }
}
