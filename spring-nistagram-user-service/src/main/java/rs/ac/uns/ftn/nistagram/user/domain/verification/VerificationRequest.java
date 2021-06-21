package rs.ac.uns.ftn.nistagram.user.domain.verification;

import lombok.Getter;
import lombok.Setter;
import rs.ac.uns.ftn.nistagram.user.domain.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class VerificationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    private User user;
    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    private Category category;
    @NotBlank
    private String imageUrl;
    @NotNull
    private VerificationStatus status;

    protected VerificationRequest() {}

    public VerificationRequest(Category category, String imageUrl) {
        this.category = category;
        this.imageUrl = imageUrl;
        this.status = VerificationStatus.PENDING;
    }
}
