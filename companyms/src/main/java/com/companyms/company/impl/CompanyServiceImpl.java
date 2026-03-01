package com.companyms.company.impl;

import com.companyms.company.Company;
import com.companyms.company.CompanyRepository;
import com.companyms.company.CompanyService;
import com.companyms.company.CompanyVO;
import com.companyms.company.clients.ReviewClient;
import com.companyms.company.config.ReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ReviewClient reviewClient;

    @Override
    public List<CompanyVO> getAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateCompany(CompanyVO companyVO, UUID id) {

        Optional<Company> companyOptional = companyRepository.findById(id);

        if (companyOptional.isPresent()) {

            Company existingCompany = companyOptional.get();
            existingCompany.setName(companyVO.getName());
            existingCompany.setDescription(companyVO.getDescription());

            companyRepository.save(existingCompany);
            return true;
        }
        return false;
    }

    @Override
    public void createCompany(CompanyVO companyVO) {

        Company company = convertToEntity(companyVO);
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompanyById(UUID id) {

        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public CompanyVO getCompanyById(UUID id) {

        return companyRepository.findById(id)
                .map(this::convertToVO)
                .orElse(null);
    }

    @Override
    public void updateCompanyReview(ReviewVO reviewVO) {
        Optional<Company> company = companyRepository.findById(reviewVO.getCompanyId());
        if (company.isPresent()) {
            Company existingCompany = company.get();
            Double rating = reviewClient.getReviews(reviewVO.getCompanyId());
            existingCompany.setRating(rating);
            companyRepository.save(existingCompany);
        }
    }


    private CompanyVO convertToVO(Company company) {

        if (company == null) return null;

        return CompanyVO.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .rating(Objects.requireNonNullElse(company.getRating(), 0.0))
                .build();
    }

    private Company convertToEntity(CompanyVO companyVO) {

        if (companyVO == null) return null;

        return Company.builder()
                .id(companyVO.getId())
                .name(companyVO.getName())
                .description(companyVO.getDescription())
                .build();
    }
}