package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
class SliceTests {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    PersonService personService;

    @Test
    @DisplayName("Testing controller and service with a given object")
    void doControllerAndServiceTests() throws Exception {

        given(personService
                .findById(99))
                .willReturn(new Person(99, "John Doe", 172, 80, null));

        mockMvc.perform(get("/api/person/99"))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("name").value("John Doe"))
                        .andExpect(jsonPath("height").value(172))
                        .andExpect(jsonPath("weight").value(80));

        verify(personService).findById(99);

    }

}
