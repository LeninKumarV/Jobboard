package com.companyms.company;

import java.util.List;
import java.util.UUID;

public interface CompanyService {
    List<CompanyVO> getAllCompanies();
    boolean updateCompany(CompanyVO company, UUID id);
    void createCompany(CompanyVO company);
    boolean deleteCompanyById(UUID id);
    CompanyVO getCompanyById(UUID id);
}
