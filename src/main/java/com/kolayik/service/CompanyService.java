package com.kolayik.service;

import com.kolayik.dto.request.AddCompanyRequestDto;
import com.kolayik.entity.Company;
import com.kolayik.entity.User;
import com.kolayik.exception.ErrorType;
import com.kolayik.exception.KolayIkException;
import com.kolayik.repository.CompanyRepository;
import com.kolayik.repository.UserRepository;
import com.kolayik.utility.enums.Status;
import com.kolayik.view.VwCompany;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public void addCompany(AddCompanyRequestDto dto,Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Kullanıcı bulunamadı.");
        }

        Company company = Company.builder()
                .name(dto.name())
                .phone(dto.phone())
                .userId(optionalUser.get().getId())
                .address(dto.address())
                .sector(dto.sector())
                .status(Status.ASKIDA)
                .build();
        companyRepository.save(company);
        User user = optionalUser.get();
        user.setCompanyId(company.getId());
        userRepository.save(user);
    }

    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    public void approved(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new KolayIkException(ErrorType.COMPANY_NOT_FOUND));

        company.setStatus(Status.AKTIF);
        companyRepository.save(company);
    }
    public void reject(Long companyId ) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new KolayIkException(ErrorType.COMPANY_NOT_FOUND));

        company.setStatus(Status.PASIF);
        companyRepository.save(company);
    }

    public void editCompany(Long companyId,AddCompanyRequestDto dto) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new KolayIkException(ErrorType.COMPANY_NOT_FOUND));

        company.setName(dto.name());
        company.setPhone(dto.phone());
        company.setAddress(dto.address());
        company.setSector(dto.sector());
        companyRepository.save(company);

    }


    public List<VwCompany> getVwCompany() {
        return companyRepository.getAllCompany();
    }



}
