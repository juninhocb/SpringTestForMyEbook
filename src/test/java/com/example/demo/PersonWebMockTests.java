package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("mysql-tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PersonWebMockTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Test
    @Transactional
    void testWebMethods() throws Exception {

        var people = PersonUtils.getPeople();

        String personAsJson = objectMapper
                .writeValueAsString(people.getFirst());

        mockMvc.perform(get("/api/person"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/person/{id}", "1"))
                .andDo(print()).andExpect(status().isOk());

        mockMvc.perform(get("/api/person/999999"))
                .andExpect(status().isNotFound());

        mockMvc.perform(
                post("/api/person")
                    .content(personAsJson)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }
}
