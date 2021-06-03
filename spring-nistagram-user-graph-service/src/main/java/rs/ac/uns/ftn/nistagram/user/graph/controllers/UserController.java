package rs.ac.uns.ftn.nistagram.user.graph.controllers;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.user.graph.domain.User;
import rs.ac.uns.ftn.nistagram.user.graph.payload.UserRelationshipRequest;
import rs.ac.uns.ftn.nistagram.user.graph.payload.UserPayload;
import rs.ac.uns.ftn.nistagram.user.graph.services.UserService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("api/user-graph/")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService) {
        this.userService = userService;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserPayload userPayload){
        userService.create(modelMapper.map(userPayload, User.class));

        return ResponseEntity.ok("User has been successfully created");
    }



}
