package br.com.luizaugustocs.teambuilder.controller;

import br.com.luizaugustocs.teambuilder.ControllerIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerIntegrationTest
public class UserControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Test
    public void deleteUser_notFound() throws Exception {
        mockMvc.perform(delete("/users/8851e062-8988-46c8-bc7a-cbca77797d3d")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Unable to find user with id '8851e062-8988-46c8-bc7a-cbca77797d3d'.")))
                .andExpect(jsonPath("$.path", equalTo("/users/8851e062-8988-46c8-bc7a-cbca77797d3d")))
                .andExpect(jsonPath("$.status", equalTo("NOT_FOUND")))
        ;
    }

    @Test
    public void deleteUser_isTeamLead() throws Exception {
        mockMvc.perform(delete("/users/8851e062-8988-46c8-bc7a-cbca77797d3c")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("The user [8851e062-8988-46c8-bc7a-cbca77797d3c] cannot be removed because it is a team lead.")))
                .andExpect(jsonPath("$.path", equalTo("/users/8851e062-8988-46c8-bc7a-cbca77797d3c")))
                .andExpect(jsonPath("$.status", equalTo("BAD_REQUEST")))
        ;
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/8311db28-662d-4e39-967e-3512e8ece4a8")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
        ;


    }

}