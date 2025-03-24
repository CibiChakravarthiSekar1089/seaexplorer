package com.maveric.seaexplorer.controller.res;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Obstacle {
    private Integer x;
    private Integer y;
    private String updatedBy;
    private Date updatedAt;
}
