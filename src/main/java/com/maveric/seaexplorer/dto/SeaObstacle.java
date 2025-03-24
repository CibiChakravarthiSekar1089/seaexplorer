package com.maveric.seaexplorer.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeaObstacle {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Integer x;
    private Integer y;
    private String updatedBy;
    private Date updatedAt;
}
