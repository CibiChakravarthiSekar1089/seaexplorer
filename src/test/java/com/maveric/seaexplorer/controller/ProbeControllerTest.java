package com.maveric.seaexplorer.controller;

import com.maveric.seaexplorer.BaseControllerTest;
import com.maveric.seaexplorer.controller.req.Coordinate;
import com.maveric.seaexplorer.controller.req.ProbeLocation;
import com.maveric.seaexplorer.service.ProbeVisitHistoryService;
import com.maveric.seaexplorer.service.SeaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"security.basic.enabled=false"})
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
@AutoConfigureMockMvc(addFilters = false)
public class ProbeControllerTest extends BaseControllerTest {

    @Mock
    private ProbeVisitHistoryService probeService;

    @Mock
    private SeaService seaService;

    @InjectMocks
    private ProbeController controller;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void apiProbeLocation() throws Exception {
        when(seaService.getProbeCoordinate())
                .thenReturn(ProbeLocation.builder()
                        .coordinate(new Coordinate(1, 1))
                        .headDirection(ProbeLocation.HeadDirection.NORTH)
                        .build());
        mockMvc.perform(get("/probe"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "coordinate": {
                                "x": 1,
                                "y": 1
                            },
                            "headDirection": "NORTH" 
                        }
                    """));
    }

    @Test
    public void apiProbeListMoves() throws Exception {
        when(probeService.listProbeVisitHistory(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/probe/move")).andExpect(status().isBadRequest());
        mockMvc.perform(get("/probe/move")
                .queryParam("page", "0")
                .queryParam("pageSize", "1")).andExpect(status().isBadRequest());
        mockMvc.perform(get("/probe/move")
                .queryParam("page", "0")
                .queryParam("pageSize", "5")).andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void apiProbeMoveUpdate() throws Exception {
        mockMvc.perform(post("/probe/move")).andExpect(status().isBadRequest());
        mockMvc.perform(post("/probe/move").contentType(MediaType.APPLICATION_JSON).content("""
                   {}
                """)).andExpect(status().is4xxClientError());
        mockMvc.perform(post("/probe/move").contentType(MediaType.APPLICATION_JSON).content("""
                   {"direction": "FRONTS"}
                """)).andExpect(status().is4xxClientError());
        mockMvc.perform(post("/probe/move").contentType(MediaType.APPLICATION_JSON).content("""
                   {"direction": "FRONT"}
                """)).andExpect(status().isOk());
    }
}
