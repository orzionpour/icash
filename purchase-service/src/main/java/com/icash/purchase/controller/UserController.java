package com.icash.purchase.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icash.purchase.entity.User;
import com.icash.purchase.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/count")
    public ResponseEntity<Long> countUsers() {
        Long userCounter = userRepository.count();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userCounter);
    }

    @GetMapping("/frequent-buyers")
    public ResponseEntity<List<User>> getUsersWithMinPurchases(
        @RequestParam(defaultValue = "3") int minPurchases
    ) {
        List<User> users = userRepository.findUsersWithAtLeastNPurchases(minPurchases);
        return ResponseEntity.ok(users);
    }
}
