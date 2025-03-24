package com.maveric.seaexplorer.controller;

import com.maveric.seaexplorer.controller.req.ProbeLocation;
import com.maveric.seaexplorer.service.SeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @Autowired
    private SeaService seaService;

    @PostMapping("/home")
    public ProbeLocation probeLocation(){
        return seaService.getProbeCoordinate();
    }
}
