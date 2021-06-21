package rs.ac.uns.ftn.nistagram.auth.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.auth.controllers.dtos.AuthRequestDTO;
import rs.ac.uns.ftn.nistagram.auth.controllers.dtos.AuthTokenDTO;
import rs.ac.uns.ftn.nistagram.auth.controllers.dtos.PasswordResetRequestDTO;
import rs.ac.uns.ftn.nistagram.auth.controllers.dtos.RegistrationRequestDTO;
import rs.ac.uns.ftn.nistagram.auth.controllers.dtos.RolesDTO;
import rs.ac.uns.ftn.nistagram.auth.controllers.dtos.TokenRequestDTO;
import rs.ac.uns.ftn.nistagram.auth.controllers.mappers.AuthRequestMapper;
import rs.ac.uns.ftn.nistagram.auth.controllers.mappers.AuthTokenMapper;
import rs.ac.uns.ftn.nistagram.auth.controllers.mappers.PasswordResetRequestMapper;
import rs.ac.uns.ftn.nistagram.auth.controllers.mappers.RegistrationRequestMapper;
import rs.ac.uns.ftn.nistagram.auth.controllers.mappers.RoleMapper;
import rs.ac.uns.ftn.nistagram.auth.domain.AuthToken;
import rs.ac.uns.ftn.nistagram.auth.domain.PasswordResetBundle;
import rs.ac.uns.ftn.nistagram.auth.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthRequestMapper authMapper;
    private final RegistrationRequestMapper registrationMapper;
    private final PasswordResetRequestMapper passwordResetRequestMapper;
    private final AuthTokenMapper tokenMapper;
    private final RoleMapper roleMapper;

    @PostMapping
    public ResponseEntity<Void> authenticate(@RequestBody AuthRequestDTO authRequest) {
        String generatedToken = authService.authenticate(authMapper.toDomain(authRequest));
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, generatedToken).build();
    }

    @GetMapping
    public RolesDTO getRoles(@RequestHeader("username") String username) {
        UserDetails found = authService.getUser(username);
        return roleMapper.map(found);
    }

    @PostMapping("register")
    public String register(@Valid @RequestBody RegistrationRequestDTO registrationRequest) {
        return authService.register(registrationMapper.toDomain(registrationRequest));
    }

    @PostMapping("token")
    public AuthTokenDTO getAuthToken(@RequestBody TokenRequestDTO tokenRequest) {
        AuthToken token = authService.getAuthToken(tokenRequest.getJwt());
        return tokenMapper.toDTO(token);
    }

    @GetMapping("activate/{uuid}")
    public ResponseEntity<?> activateUser(@PathVariable String uuid) {
        authService.activate(uuid);
        return ResponseEntity.ok("Account successfully activated");
    }

    @PostMapping("request-password-reset")
    public String requestPasswordReset(@RequestBody PasswordResetRequestDTO request) {
        authService.requestPasswordReset(passwordResetRequestMapper.map(request));
        return "Password reset request successfully sent. Check your e-mail.";
    }

    @PostMapping("reset-password")
    public String resetPassword(@RequestBody @Valid PasswordResetBundle bundle) {
        authService.resetPassword(bundle);
        return "Password has been successfully reset.";
    }
}
