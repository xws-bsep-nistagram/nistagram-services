package rs.ac.uns.ftn.nistagram.user.controllers.mappers;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.VerificationRequestDTO;
import rs.ac.uns.ftn.nistagram.user.domain.user.Category;
import rs.ac.uns.ftn.nistagram.user.domain.user.request.VerificationRequest;

@Component
public class VerificationRequestMapper {

    public VerificationRequest map(VerificationRequestDTO dto) {
        Category category = new Category(dto.getCategoryId());
        return new VerificationRequest(category, dto.getImageUrl());
    }

}
