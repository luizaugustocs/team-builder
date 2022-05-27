package br.com.luizaugustocs.teambuilder.controller;

import br.com.luizaugustocs.teambuilder.ControllerIntegrationTest;
import br.com.luizaugustocs.teambuilder.dto.MembershipDTO;
import br.com.luizaugustocs.teambuilder.dto.NewRoleDTO;
import br.com.luizaugustocs.teambuilder.dto.RoleDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerIntegrationTest
public class RoleControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;


    @Test
    public void findAll() throws Exception {

        mockMvc.perform(get("/roles")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[*]", hasSize(3)))
                .andExpect(jsonPath("$.content.[*].name", containsInAnyOrder("Developer", "Tester", "Product Owner")))

        ;
    }

    @Test
    public void createRole_withoutField() throws Exception {
        mockMvc.perform(post("/roles")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Error while parsing the parameters.")))
                .andExpect(jsonPath("$.path", equalTo("/roles")))
                .andExpect(jsonPath("$.status", equalTo("BAD_REQUEST")))
                .andExpect(jsonPath("$.violations.[*]", hasSize(1)))
                .andExpect(jsonPath("$.violations.[*]", contains("Error while parsing field [name]: New roles must have a name.")))
        ;
    }

    @Test
    public void createRole_fieldNull() throws Exception {
        NewRoleDTO dto = new NewRoleDTO();

        mockMvc.perform(post("/roles")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Error while parsing the parameters.")))
                .andExpect(jsonPath("$.path", equalTo("/roles")))
                .andExpect(jsonPath("$.status", equalTo("BAD_REQUEST")))
                .andExpect(jsonPath("$.violations.[*]", hasSize(1)))
                .andExpect(jsonPath("$.violations.[*]", contains("Error while parsing field [name]: New roles must have a name.")))
        ;
    }

    @Test
    public void createRole_fieldEmptyString() throws Exception {
        NewRoleDTO dto = new NewRoleDTO();

        dto.setName("");
        mockMvc.perform(post("/roles")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Error while parsing the parameters.")))
                .andExpect(jsonPath("$.path", equalTo("/roles")))
                .andExpect(jsonPath("$.violations.[*]", hasSize(1)))
                .andExpect(jsonPath("$.violations.[*]", contains("Error while parsing field [name]: New roles must have a name.")))
        ;
    }

    @Test
    public void createRole() throws Exception {
        NewRoleDTO dto = new NewRoleDTO();
        dto.setName("Team Lead");

        var result = mockMvc.perform(post("/roles")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", equalTo("Team Lead")))
                .andReturn();

        var createdRole = objectMapper.readValue(result.getResponse().getContentAsString(), RoleDTO.class);
        assertThat(result.getResponse().getHeader("location"))
                .isEqualTo("http://localhost/roles/" + createdRole.getId());
    }

    @Test
    public void findOne_notFound() throws Exception {
        mockMvc.perform(get("/roles/e870f742-2627-426e-8287-5bbf391ea9e8")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Unable to find role with id 'e870f742-2627-426e-8287-5bbf391ea9e8'.")))
                .andExpect(jsonPath("$.path", equalTo("/roles/e870f742-2627-426e-8287-5bbf391ea9e8")))
                .andExpect(jsonPath("$.status", equalTo("NOT_FOUND")))
        ;
    }

    @Test
    public void findOne_invalidUUID() throws Exception {
        mockMvc.perform(get("/roles/abcde")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Invalid UUID string: abcde")))
                .andExpect(jsonPath("$.path", equalTo("/roles/abcde")))
                .andExpect(jsonPath("$.status", equalTo("BAD_REQUEST")))
        ;
    }

    @Test
    public void findOne() throws Exception {
        mockMvc.perform(get("/roles/e870f742-2627-426e-8287-5bbf391ea9e9")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo("e870f742-2627-426e-8287-5bbf391ea9e9")))
                .andExpect(jsonPath("$.name", equalTo("Developer")))
        ;
    }

    @Test
    public void assignRole_invalidObject() throws Exception {
        MembershipDTO dto = new MembershipDTO();
        mockMvc.perform(put("/roles/e870f742-2627-426e-8287-5bbf391ea9e9/assign")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Error while parsing the parameters.")))
                .andExpect(jsonPath("$.path", equalTo("/roles/e870f742-2627-426e-8287-5bbf391ea9e9/assign")))
                .andExpect(jsonPath("$.status", equalTo("BAD_REQUEST")))
                .andExpect(jsonPath("$.violations.[*]", hasSize(2)))
                .andExpect(jsonPath("$.violations.[*]", containsInAnyOrder("Error while parsing field [teamId]: Team id is required",
                        "Error while parsing field [userId]: User id is required")))
        ;

    }

    @Test
    public void assignRole_roleNotFound() throws Exception {
        MembershipDTO dto = new MembershipDTO();
        dto.setTeamId(UUID.randomUUID());
        dto.setUserId(UUID.randomUUID());

        mockMvc.perform(put("/roles/e870f742-2627-426e-8287-5bbf391ea9e8/assign")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Unable to find role with id 'e870f742-2627-426e-8287-5bbf391ea9e8'.")))
                .andExpect(jsonPath("$.path", equalTo("/roles/e870f742-2627-426e-8287-5bbf391ea9e8/assign")))
                .andExpect(jsonPath("$.status", equalTo("NOT_FOUND")))
        ;

    }

    @Test
    public void assignRole_userNotFound() throws Exception {
        MembershipDTO dto = new MembershipDTO();
        dto.setTeamId(UUID.fromString("062de4c6-08e2-40da-84f3-ac4d9296889e"));
        dto.setUserId(UUID.fromString("7c7bebbf-221e-4c6d-9405-577d9a730bfc"));

        mockMvc.perform(put("/roles/e870f742-2627-426e-8287-5bbf391ea9e9/assign")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Unable to find user with id '7c7bebbf-221e-4c6d-9405-577d9a730bfc'.")))
                .andExpect(jsonPath("$.path", equalTo("/roles/e870f742-2627-426e-8287-5bbf391ea9e9/assign")))
                .andExpect(jsonPath("$.status", equalTo("NOT_FOUND")))
        ;
    }

    @Test
    public void assignRole_teamNotFound() throws Exception {
        MembershipDTO dto = new MembershipDTO();
        dto.setTeamId(UUID.fromString("062de4c6-08e2-40da-84f3-ac4d9296889d"));
        dto.setUserId(UUID.fromString("7c7bebbf-221e-4c6d-9405-577d9a730bfc"));

        mockMvc.perform(put("/roles/e870f742-2627-426e-8287-5bbf391ea9e9/assign")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Unable to find team with id '062de4c6-08e2-40da-84f3-ac4d9296889d'.")))
                .andExpect(jsonPath("$.path", equalTo("/roles/e870f742-2627-426e-8287-5bbf391ea9e9/assign")))
                .andExpect(jsonPath("$.status", equalTo("NOT_FOUND")))
        ;
    }

    @Test
    public void assignRole_userNotMemberOfGroup() throws Exception {
        MembershipDTO dto = new MembershipDTO();
        dto.setTeamId(UUID.fromString("062de4c6-08e2-40da-84f3-ac4d9296889e"));
        dto.setUserId(UUID.fromString("586d6841-5ba6-47c3-91e4-8165f119f218"));

        mockMvc.perform(put("/roles/e870f742-2627-426e-8287-5bbf391ea9e9/assign")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("The user [586d6841-5ba6-47c3-91e4-8165f119f218] is not a member of the team [062de4c6-08e2-40da-84f3-ac4d9296889e].")))
                .andExpect(jsonPath("$.path", equalTo("/roles/e870f742-2627-426e-8287-5bbf391ea9e9/assign")))
                .andExpect(jsonPath("$.status", equalTo("NOT_FOUND")))
        ;
    }

    @Test
    public void assignRole() throws Exception {
        MembershipDTO dto = new MembershipDTO();
        dto.setTeamId(UUID.fromString("062de4c6-08e2-40da-84f3-ac4d9296889e"));
        dto.setUserId(UUID.fromString("8311db28-662d-4e39-967e-3512e8ece4a8"));

        mockMvc.perform(put("/roles/e870f742-2627-426e-8287-5bbf391ea9e9/assign")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", equalTo("8311db28-662d-4e39-967e-3512e8ece4a8")))
                .andExpect(jsonPath("$.teamId", equalTo("062de4c6-08e2-40da-84f3-ac4d9296889e")))
                .andExpect(jsonPath("$.roleId", equalTo("e870f742-2627-426e-8287-5bbf391ea9e9")))
        ;
    }

    @Test
    public void findByMembership_withoutParameters() throws Exception {
        mockMvc.perform(get("/roles/by-membership")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Error while parsing the parameters.")))
                .andExpect(jsonPath("$.path", equalTo("/roles/by-membership")))
                .andExpect(jsonPath("$.status", equalTo("BAD_REQUEST")))
                .andExpect(jsonPath("$.violations.[*]", hasSize(2)))
                .andExpect(jsonPath("$.violations.[*]", containsInAnyOrder("Error while parsing field [teamId]: Team id is required",
                        "Error while parsing field [userId]: User id is required")))
        ;
    }

    @Test
    public void findByMembership_teamNotFound() throws Exception {

        mockMvc.perform(get("/roles/by-membership")
                        .queryParam("teamId", "062de4c6-08e2-40da-84f3-ac4d9296889d")
                        .queryParam("userId", "7c7bebbf-221e-4c6d-9405-577d9a730bfc")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Unable to find team with id '062de4c6-08e2-40da-84f3-ac4d9296889d'.")))
                .andExpect(jsonPath("$.path", equalTo("/roles/by-membership")))
                .andExpect(jsonPath("$.status", equalTo("NOT_FOUND")))
        ;
    }

    @Test
    public void findByMembership_userNotFound() throws Exception {

        mockMvc.perform(get("/roles/by-membership")
                        .queryParam("teamId", "062de4c6-08e2-40da-84f3-ac4d9296889e")
                        .queryParam("userId", "7c7bebbf-221e-4c6d-9405-577d9a730bfc")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Unable to find user with id '7c7bebbf-221e-4c6d-9405-577d9a730bfc'.")))
                .andExpect(jsonPath("$.path", equalTo("/roles/by-membership")))
                .andExpect(jsonPath("$.status", equalTo("NOT_FOUND")))
        ;
    }

    @Test
    public void findByMembership_userNotMemberOfGroup() throws Exception {
        mockMvc.perform(get("/roles/by-membership")
                        .queryParam("teamId", "062de4c6-08e2-40da-84f3-ac4d9296889e")
                        .queryParam("userId", "586d6841-5ba6-47c3-91e4-8165f119f218")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("The user [586d6841-5ba6-47c3-91e4-8165f119f218] is not a member of the team [062de4c6-08e2-40da-84f3-ac4d9296889e].")))
                .andExpect(jsonPath("$.path", equalTo("/roles/by-membership")))
                .andExpect(jsonPath("$.status", equalTo("NOT_FOUND")))
        ;
    }

    @Test
    public void findByMembership() throws Exception {

        mockMvc.perform(get("/roles/by-membership")
                        .queryParam("teamId", "062de4c6-08e2-40da-84f3-ac4d9296889e")
                        .queryParam("userId", "8311db28-662d-4e39-967e-3512e8ece4a8")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo("11e30283-a92a-42fb-add8-b6c9eaf85e29")))
                .andExpect(jsonPath("$.name", equalTo("Tester")))
        ;
    }

    @Test
    public void getMemberships_roleNotFound() throws Exception {
        mockMvc.perform(get("/roles/e870f742-2627-426e-8287-5bbf391ea9e8/memberships")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Unable to find role with id 'e870f742-2627-426e-8287-5bbf391ea9e8'.")))
                .andExpect(jsonPath("$.path", equalTo("/roles/e870f742-2627-426e-8287-5bbf391ea9e8/memberships")))
                .andExpect(jsonPath("$.status", equalTo("NOT_FOUND")))
        ;
    }

    @Test
    public void getMemberships_noResults() throws Exception {
        mockMvc.perform(get("/roles/d5ddba22-54b9-4a16-acfd-84c1f5370567/memberships")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[*]", hasSize(0)))
        ;
    }

    @Test
    public void getMemberships() throws Exception {
        mockMvc.perform(get("/roles/e870f742-2627-426e-8287-5bbf391ea9e9/memberships")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.content.[*]", hasSize(2)))
                .andExpect(jsonPath("$.content.[*].teamId", containsInAnyOrder(
                        "062de4c6-08e2-40da-84f3-ac4d9296889e",
                        "ebf26e6d-7ab9-4e93-b233-f28d5f0a93ed")))
                .andExpect(jsonPath("$.content.[*].userId", containsInAnyOrder(
                        "8851e062-8988-46c8-bc7a-cbca77797d3c",
                        "8311db28-662d-4e39-967e-3512e8ece4a8")))
        ;

    }

    @Test
    public void deleteRole_notFound() throws Exception {

        mockMvc.perform(delete("/roles/e870f742-2627-426e-8287-5bbf391ea9e8")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Unable to find role with id 'e870f742-2627-426e-8287-5bbf391ea9e8'.")))
                .andExpect(jsonPath("$.path", equalTo("/roles/e870f742-2627-426e-8287-5bbf391ea9e8")))
                .andExpect(jsonPath("$.status", equalTo("NOT_FOUND")))
        ;
    }

    @Test
    public void deleteRole_defaultRole() throws Exception {
        mockMvc.perform(delete("/roles/e870f742-2627-426e-8287-5bbf391ea9e9")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("It is not possible to delete the default role.")))
                .andExpect(jsonPath("$.path", equalTo("/roles/e870f742-2627-426e-8287-5bbf391ea9e9")))
                .andExpect(jsonPath("$.status", equalTo("BAD_REQUEST")))
        ;
    }

    @Test
    @Sql(statements = "update role set is_default = false where is_default = true")
    public void deleteRole_noDefaultRole() throws Exception {
        mockMvc.perform(delete("/roles/11e30283-a92a-42fb-add8-b6c9eaf85e29")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Unable to find default role.")))
                .andExpect(jsonPath("$.path", equalTo("/roles/11e30283-a92a-42fb-add8-b6c9eaf85e29")))
                .andExpect(jsonPath("$.status", equalTo("NOT_FOUND")))
        ;
    }

    @Test
    @Sql(statements = "update role set is_default = true where id = 'd5ddba22-54b9-4a16-acfd-84c1f5370567'")
    public void deleteRole_moreThanOneDefaultRole() throws Exception {
        mockMvc.perform(delete("/roles/11e30283-a92a-42fb-add8-b6c9eaf85e29")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.time", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("More than one default role found.")))
                .andExpect(jsonPath("$.path", equalTo("/roles/11e30283-a92a-42fb-add8-b6c9eaf85e29")))
                .andExpect(jsonPath("$.status", equalTo("BAD_REQUEST")))
        ;
    }

    @Test
    public void deleteRole() throws Exception {
        mockMvc.perform(delete("/roles/11e30283-a92a-42fb-add8-b6c9eaf85e29")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/roles/by-membership")
                        .queryParam("teamId", "062de4c6-08e2-40da-84f3-ac4d9296889e")
                        .queryParam("userId", "8311db28-662d-4e39-967e-3512e8ece4a8")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo("e870f742-2627-426e-8287-5bbf391ea9e9")))
                .andExpect(jsonPath("$.name", equalTo("Developer")))

                ;
    }

}