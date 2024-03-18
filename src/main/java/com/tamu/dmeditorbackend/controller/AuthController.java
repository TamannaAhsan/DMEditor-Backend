package com.tamu.dmeditorbackend.controller;

import com.tamu.dmeditorbackend.entity.User;
import com.tamu.dmeditorbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User payload){
        return ResponseEntity.ok(userService.login(payload));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User payload){
        return ResponseEntity.ok(userService.createUser(payload));
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody User payload){
        try{
            return ResponseEntity.ok(userService.validateToken(payload.getToken()));
        } catch (Exception e){
            return ResponseEntity.ok(Boolean.FALSE);
        }

    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable Long id) {
        User updatedUser = userService.updateUser(user, id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> all = userService.getAllUser();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PostMapping("/vendor")
    public ResponseEntity<?> getVendorId(@RequestBody User payload){
        try{
            return ResponseEntity.ok(userService.vendorId(payload.getToken()));
        } catch (Exception e){
            return ResponseEntity.ok(null);
        }
    }

    @PostMapping("/email")
    public ResponseEntity<?> getVendorEmail(@RequestBody User payload){
        try{
            return ResponseEntity.ok(userService.vendorEmail(payload.getToken()));
        } catch (Exception e){
            return ResponseEntity.ok(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete (@PathVariable Long id){
        userService.deleteUser(id);
        Map<String, String> message = Map.of("message", "User Deleted Successfully");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
