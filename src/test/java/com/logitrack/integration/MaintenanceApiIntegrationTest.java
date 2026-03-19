package com.logitrack.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class MaintenanceApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
                void shouldCreateMaintenanceWhenPayloadIsValid() throws Exception {
                                String token = loginAndGetToken();

                                String payload = """
                                                                {
                                                                        "vehicleId": 1,
                                                                        "serviceType": "OLEO",
                                                                        "scheduledDate": "2026-03-25",
                                                                        "status": "PENDENTE",
                                                                        "description": "troca preventiva"
                                                                }
                                                                """;

                                mockMvc.perform(post("/api/maintenance")
                                                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                                                .header("Authorization", "Bearer " + token)
                                                                                                .content(payload))
                                                                .andExpect(status().isCreated())
                                                                .andExpect(jsonPath("$.vehicleId").value(1))
                                                                .andExpect(jsonPath("$.serviceType").value("OLEO"))
                                                                .andExpect(jsonPath("$.status").value("PENDENTE"));
                }

                @Test
    void shouldReturnApiErrorWhenVehicleDoesNotExist() throws Exception {
        String token = loginAndGetToken();

        String payload = """
                {
                  "vehicleId": 999999,
                  "serviceType": "OLEO",
                  "scheduledDate": "2026-03-20",
                  "status": "PENDENTE",
                  "description": "troca"
                }
                """;

        mockMvc.perform(post("/api/maintenance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Veiculo nao encontrado"))
                .andExpect(jsonPath("$.path").value("/api/maintenance"));
    }

    private String loginAndGetToken() throws Exception {
        String loginPayload = """
                {
                  "username": "admin",
                  "password": "admin"
                }
                """;

        String responseBody = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginPayload))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode node = objectMapper.readTree(responseBody);
        return node.get("token").asText();
    }
}
