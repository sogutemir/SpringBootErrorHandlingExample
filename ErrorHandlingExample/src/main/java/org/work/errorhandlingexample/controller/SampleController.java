package org.work.errorhandlingexample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.work.errorhandlingexample.exception.CustomException;
import org.work.errorhandlingexample.model.SampleModel;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sample")
@Validated
public class SampleController {

    @PostMapping
    public ResponseEntity<SampleModel> createSample(@Valid @RequestBody SampleModel sampleModel) {
        if ("error".equalsIgnoreCase(sampleModel.getValue())) {
            throw new CustomException("Custom error occurred");
        }
        return new ResponseEntity<>(sampleModel, HttpStatus.CREATED);
    }

    @GetMapping("/{value}")
    public ResponseEntity<SampleModel> getSample(@PathVariable String value) {
        if ("error".equalsIgnoreCase(value)) {
            throw new CustomException("Custom error occurred");
        }
        return new ResponseEntity<>(new SampleModel(value), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
