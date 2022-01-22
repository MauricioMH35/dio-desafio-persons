package org.dio.desafio.controllers;

import org.dio.desafio.dto.responses.ResponseDTO;
import org.dio.desafio.utils.JsonUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.dio.desafio.utils.PersonUtil.createDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonController controller;

    @Test
    @DisplayName("When Save Person Then Succeeded")
    public void save_Test() throws Exception {
        mockMvc.perform(post("/v1/persons/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(createDTO())))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("When Find By Id Person Then Succeeded")
    public void findById_Test() throws Exception {
        mockMvc.perform(get("/v1/persons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());
    }

    @Test
    @DisplayName("When Find All Person Then Succeeded")
    public void findAll() throws Exception {
        mockMvc.perform(get("/v1/persons/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());
    }

    @Test
    @DisplayName("When Update By Id Person Then Succeeded")
    public void updateById_Test() throws Exception {
        mockMvc.perform(put("/v1/persons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(createDTO())))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When Delete By Id Person Then Succeeded")
    public void deleteById_Test() throws Exception {
        mockMvc.perform(delete("/v1/persons/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private ResponseDTO createResponseMessage(String message, Long id) {
        return ResponseDTO.builder()
                .message(message + id)
                .build();
    }

    private ResponseDTO createGenericResponseMessage(String message) {
        return ResponseDTO.builder()
                .message(message)
                .build();
    }

}
