package com.maveric.seaexplorer.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static com.maveric.seaexplorer.controller.req.ProbeLocation.HeadDirection;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProbeVisitHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private HeadDirection headDirection;
    private boolean ignoreObstacle;
    private int coordinateX;
    private int coordinateY;
    private String updatedBy;
    private Date updatedAt;
}
