package rs.ac.uns.ftn.nistagram.user.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.verification.CategoryDTO;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.verification.VerificationRequestDTO;
import rs.ac.uns.ftn.nistagram.user.controllers.dtos.verification.VerificationRequestViewDTO;
import rs.ac.uns.ftn.nistagram.user.controllers.mappers.VerificationRequestMapper;
import rs.ac.uns.ftn.nistagram.user.domain.verification.Category;
import rs.ac.uns.ftn.nistagram.user.domain.verification.VerificationRequest;
import rs.ac.uns.ftn.nistagram.user.service.CategoryService;
import rs.ac.uns.ftn.nistagram.user.service.VerificationService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("api/users/verification")
public class VerificationController {

    private final VerificationService verificationService;
    private final CategoryService categoryService;
    private final VerificationRequestMapper mapper;

    @PostMapping
    public String create(@RequestHeader("username") String username, @Valid @RequestBody VerificationRequestDTO request) {
        verificationService.create(username, mapper.map(request));
        return "Verification request successfully registered.";
    }

    @GetMapping("categories")
    public List<CategoryDTO> getCategories() {
        List<Category> all = categoryService.getAll();
        return all.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping("admin")
    public List<VerificationRequestViewDTO> getAllPending() {
        List<VerificationRequest> pending = verificationService.getPending();
        return pending.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @PutMapping("admin/{id}")
    public String verify(@PathVariable Long id) {
        verificationService.verify(id);
        return "User successfully verified.";
    }

    @DeleteMapping("admin/{id}")
    public String decline(@PathVariable Long id) {
        verificationService.decline(id);
        return "User verification declined.";
    }

}
