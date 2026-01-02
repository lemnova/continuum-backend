package tech.lemnova.continuum_backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.lemnova.continuum_backend.dtos.user.UserDTO;
import tech.lemnova.continuum_backend.entities.User;
import tech.lemnova.continuum_backend.services.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody User user) {
        String response = userService.create(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<UserDTO> read(@RequestParam long id) {
        return ResponseEntity.ok(userService.read(id));
    }

    @PutMapping
    public ResponseEntity<String> update(
        @RequestParam long id,
        @RequestBody User user
    ) {
        String response = userService.update(id, user);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
