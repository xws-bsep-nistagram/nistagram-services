package rs.ac.uns.ftn.nistagram.user.controllers.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.CategoryDTO;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.VerificationRequestDTO;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.VerificationRequestViewDTO;
import rs.ac.uns.ftn.nistagram.user.domain.verification.Category;
import rs.ac.uns.ftn.nistagram.user.domain.verification.VerificationRequest;

@AllArgsConstructor
@Component
public class VerificationRequestMapper {

    private final ProfileMapper profileMapper;

    public VerificationRequest map(VerificationRequestDTO dto) {
        Category category = new Category(dto.getCategoryId());
        return new VerificationRequest(category, dto.getImageUrl());
    }

    public VerificationRequestViewDTO map (VerificationRequest domain) {
        VerificationRequestViewDTO dto = new VerificationRequestViewDTO();
        CategoryDTO category = map(domain.getCategory());
        dto.setId(domain.getId());
        dto.setCategory(category);
        dto.setImageUrl(domain.getImageUrl());
        dto.setStatus(domain.getStatus());
        dto.setProfile(profileMapper.map(domain.getUser()));
        return dto;
    }

    public CategoryDTO map(Category domain) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(domain.getId());
        dto.setName(domain.getName());
        return dto;
    }

}
