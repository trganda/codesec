package com.github.trganda.validation.controller.parameters;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class ValidateParametersController {
    @GetMapping("/validatePathVariable/{id}")
    ResponseEntity<String> validatePathVariable(@PathVariable("id") @Min(5) int id) {
        return ResponseEntity.ok("valid");
    }

    @GetMapping("/validateRequestParameter")
    ResponseEntity<String> validateRequestParameter(@RequestParam("param") @Min(5) int param) {
        return ResponseEntity.ok("valid");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    String handleConstraintViolationException(ConstraintViolationException e) {
        return "not valid due to validation error: " + e.getMessage();
    }
}
