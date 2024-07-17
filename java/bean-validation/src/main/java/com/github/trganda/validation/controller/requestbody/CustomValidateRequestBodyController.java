package com.github.trganda.validation.controller.requestbody;

import com.github.trganda.validation.InputWithCustomValidator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomValidateRequestBodyController {

    @PostMapping("/customValidateBody")
    ResponseEntity<String> validateBody(@Valid @RequestBody InputWithCustomValidator input) {
        return ResponseEntity.ok("valid");
    }
}
