package com.companyms.company;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("all/companies")
    public ResponseEntity<List<CompanyVO>> getAllCompanies(){
        return new ResponseEntity<>(companyService.getAllCompanies(),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable UUID id,
                                                @RequestBody CompanyVO company){
        companyService.updateCompany(company, id);
        return new ResponseEntity<>("Company updated successfully",
                HttpStatus.OK);
    }

    @PostMapping("/save/company")
    public ResponseEntity<String> addCompany(@RequestBody CompanyVO company){
        companyService.createCompany(company);
        return new ResponseEntity<>("Company added successfully",
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable UUID id){
        boolean isDeleted = companyService.deleteCompanyById(id);
        if (isDeleted) {
            return new ResponseEntity<>("Company Successfully Deleted",
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Company Not Found",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyVO> getCompany(@PathVariable UUID id){
        CompanyVO company = companyService.getCompanyById(id);
        if (company != null){
            return new ResponseEntity<>(company, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
