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
public class TeamControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Test
    public void deleteTeam_notFound() throws Exception {
        mockMvc.perform(delete("/teams/8851e062-8988-46c8-bc7a-cbca77797d3d")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Unable to find team with id '8851e062-8988-46c8-bc7a-cbca77797d3d'.")))
                .andExpect(jsonPath("$.path", equalTo("/teams/8851e062-8988-46c8-bc7a-cbca77797d3d")))
                .andExpect(jsonPath("$.status", equalTo("NOT_FOUND")))
        ;
    }


    @Test
    public void deleteTeam() throws Exception {
        mockMvc.perform(delete("/teams/ebf26e6d-7ab9-4e93-b233-f28d5f0a93ed")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
        ;


    }
}