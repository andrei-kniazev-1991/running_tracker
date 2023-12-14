package com.runningtracker.demo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runningtracker.controllers.user.UserCreateDto;
import com.runningtracker.data.user.UserEntity;
import com.runningtracker.data.user.UserRepository;
import com.runningtracker.utils.models.UserGender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        this.userRepository.deleteAll();
    }

    @Test
    public void testGetAllUsers_authenticated_returnsAllUsers() throws Exception {
        List<UserEntity> users = createUsers(5);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(users.size())).andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].id").value(users.get(0).getId())).andExpect(jsonPath("$[1].id").exists())
                .andExpect(jsonPath("$[1].id").value(users.get(1).getId())).andExpect(jsonPath("$[2].id").exists())
                .andExpect(jsonPath("$[2].id").value(users.get(2).getId())).andExpect(jsonPath("$[3].id").exists())
                .andExpect(jsonPath("$[3].id").value(users.get(3).getId())).andExpect(jsonPath("$[4].id").exists())
                .andExpect(jsonPath("$[4].id").value(users.get(4).getId()));
    }

    @Test
    public void testGetAllUsers_notAuthenticated_401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")).andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetUserById_authenticated_returnsUser() throws Exception {
        List<UserEntity> userEntities = createUsers(1);
        UserEntity user = userEntities.get(0);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{userId}", user.getId())
                .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.id").value(user.getId()));
    }

    @Test
    public void testGetUserById_notAuthenticated_401() throws Exception {
        Long userId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{userId}", userId)).andExpect(status().isUnauthorized());
    }

    @Test
    public void testAddUser_authenticated_addsNewUser() throws Exception {
        List<UserEntity> users = createUsers(1, false);
        UserEntity user = users.get(0);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .with(SecurityMockMvcRequestPostProcessors.user("username").password("password"))
                .content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        assertEquals(userRepository.findAll().get(0).getFirstName(), user.getFirstName());
    }

    @Test
    public void testAddUser_notAuthenticated_401() throws Exception {
        List<UserEntity> users = createUsers(1, false);
        UserEntity user = users.get(0);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users").content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
    }

    @Test
    public void testUpdateUser_authenticated_updatesUser() throws Exception {
        List<UserEntity> users = createUsers(1);
        UserEntity user = users.get(0);
        user.setLastName("changed");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{userId}", user.getId())
                .with(SecurityMockMvcRequestPostProcessors.user("username").password("password"))
                .content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(userRepository.findAll().get(0).getFirstName(), user.getFirstName());
    }

    @Test
    public void testUpdateUser_notAuthenticated_401() throws Exception {
        Long userId = 1L;
        UserCreateDto updatedUser = new UserCreateDto();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{userId}", userId)
                .content(objectMapper.writeValueAsString(updatedUser)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeleteUser_authenticated_deletesUser() throws Exception {
        List<UserEntity> users = createUsers(1);
        UserEntity user = users.get(0);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{userId}", user.getId())
                .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().isNoContent());
        assertEquals(userRepository.findAll().size(), 0);
    }

    @Test
    public void testDeleteUser_notAuthenticated_401() throws Exception {
        Long userId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{userId}", userId))
                .andExpect(status().isUnauthorized());
    }

    private List<UserEntity> createUsers(int count, boolean writeToDb) {
        List<UserEntity> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            UserEntity user = new UserEntity();
            user.setFirstName("first" + i);
            user.setLastName("last" + i);
            user.setBirthDate(new Date());
            user.setGender(UserGender.OTHER);
            if (writeToDb) {
                result.add(userRepository.save(user));
            } else {
                result.add(user);
            }
        }
        return result;
    }

    private List<UserEntity> createUsers(int count) {
        return createUsers(count, true);
    }
}

