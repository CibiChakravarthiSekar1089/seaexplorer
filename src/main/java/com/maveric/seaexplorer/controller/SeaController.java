package com.maveric.seaexplorer.controller;

import com.maveric.seaexplorer.controller.req.NewObstacle;
import com.maveric.seaexplorer.controller.res.Obstacle;
import com.maveric.seaexplorer.service.SeaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/sea")
@RestController
public class SeaController {

    @Autowired
    private SeaService seaService;

    @GetMapping("/obstacles")
    @Secured("ROLE_VIEW_SEA_OBSTACLES")
    public List<Obstacle> listObstacles(){
        return seaService.getSeaObstacles();
    }

    @PostMapping("/obstacles")
    @Secured("ROLE_UPDATE_SEA_OBSTACLES")
    public ResponseEntity createNewObstacle(@RequestBody @Valid NewObstacle obstacle){
        seaService.createNewObstacle(obstacle);
        return ResponseEntity.ok().build();
    }
}
