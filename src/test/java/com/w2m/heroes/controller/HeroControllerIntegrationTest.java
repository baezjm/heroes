package com.w2m.heroes.controller;

import com.w2m.heroes.repository.HeroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class HeroControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HeroRepository repository;

    @Test
    @WithMockUser(username="user", password = "user", authorities = "USER")
    void test_get_by_id_one_is_ok() throws Exception {

        mockMvc.perform(get("/api/hero/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1,'name':'batman'}"));
    }

    @Test
    @WithMockUser(username="user", password = "user", authorities = "USER")
    void test_get_by_id_three_is_not_found() throws Exception {
        mockMvc.perform(get("/api/hero/{id}", 3))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username="admin", password = "admin", authorities = "ADMIN")
    void test_create_a_hero() throws Exception {
        int previousSize = repository.findAll().size();
        mockMvc.perform(post("/api/hero/")
                        .contentType("application/json;charset=UTF-8")
                        .content("{\"name\":\"spiderman\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'id':3,'name':'spiderman'}"));
        assertThat(repository.findAll().size()).isEqualTo(previousSize + 1);
    }

    @Test
    @WithMockUser(username="admin", password = "admin", authorities = "ADMIN")
    void test_create_a_hero_without_name() throws Exception {
        mockMvc.perform(post("/api/hero/")
                        .contentType("application/json;charset=UTF-8")
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username="admin", password = "admin", authorities = "ADMIN")
    void test_update_a_hero() throws Exception {
        mockMvc.perform(put("/api/hero/{id}",1)
                        .contentType("application/json;charset=UTF-8")
                        .content("{\"name\":\"spiderman\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'id':1,'name':'spiderman'}"));

        assertThat(repository.findById(1L).get().getName()).isEqualTo("spiderman");
    }

    @Test
    @WithMockUser(username="admin", password = "admin", authorities = "ADMIN")
    void test_update_a_hero_without_name() throws Exception {
        mockMvc.perform(put("/api/hero/{id}",1)
                        .contentType("application/json;charset=UTF-8")
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username="admin", password = "admin", authorities = "ADMIN")
    void test_update_a_hero_not_found() throws Exception {
        mockMvc.perform(put("/api/hero/{id}",4)
                        .contentType("application/json;charset=UTF-8")
                        .content("{\"name\":\"spiderman\"}"))
                .andExpect(status().isNotFound());
    }
}