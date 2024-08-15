// File: `ErrorHandlingExample/src/test/java/org/work/errorhandlingexample/controller/SampleControllerTest.java`
package org.work.errorhandlingexample.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SampleController.class)
public class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateSample_ValidInput() throws Exception {
        String sampleJson = "{\"value\":\"valid\"}";

        mockMvc.perform(post("/api/sample")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(sampleJson));
    }

    @Test
    public void testCreateSample_InvalidInput() throws Exception {
        String sampleJson = "{\"value\":\"\"}";

        mockMvc.perform(post("/api/sample")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateSample_CustomException() throws Exception {
        String sampleJson = "{\"value\":\"error\"}";

        mockMvc.perform(post("/api/sample")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":400,\"message\":\"Custom error occurred\"}"));
    }


    @Test
    public void testGetSample_CustomException() throws Exception {
        String value = "error";

        mockMvc.perform(get("/api/sample/" + value))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":400,\"message\":\"Custom error occurred\"}"));
    }
}