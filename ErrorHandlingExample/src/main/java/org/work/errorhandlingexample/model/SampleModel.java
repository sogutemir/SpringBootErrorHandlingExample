package org.work.errorhandlingexample.model;

import jakarta.validation.constraints.NotEmpty;

import javax.validation.constraints.NotBlank;

public class SampleModel {

    @NotEmpty(message = "Value must not be empty")
    private String value;


    public SampleModel() {
    }

    public SampleModel(String value) {
        this.value = value;
    }

    public @NotBlank(message = "Value cannot be blank") String getValue() {
        return value;
    }

    public void setValue(@NotBlank(message = "Value cannot be blank") String value) {
        this.value = value;
    }
}