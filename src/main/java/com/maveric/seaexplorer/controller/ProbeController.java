package com.maveric.seaexplorer.controller;

import com.maveric.seaexplorer.controller.req.ProbeLocation;
import com.maveric.seaexplorer.controller.req.ProbeNextCoordinate;
import com.maveric.seaexplorer.controller.res.ProbeMove;
import com.maveric.seaexplorer.service.ProbeVisitHistoryService;
import com.maveric.seaexplorer.service.SeaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("probe")
@RestController
public class ProbeController {

    @Autowired
    private ProbeVisitHistoryService service;

    @Autowired
    private SeaService seaService;


    @GetMapping
    @Secured("ROLE_VIEW_PROBE")
    public ProbeLocation probeLocation(){
        return seaService.getProbeCoordinate();
    }

    @GetMapping("/move")
    @Secured("ROLE_VIEW_PROBE")
    public List<ProbeMove> listProbeVisitHistory(
            @NotNull @Min(0) Integer page,
            @NotNull @Min(5) Integer pageSize
    ){
        return service.listProbeVisitHistory(page, pageSize);
    }

    @PostMapping("/move")
    @Secured("ROLE_UPDATE_PROBE_MOVES")
    public ResponseEntity moveProbeNextCoordinate(@RequestBody @Valid ProbeNextCoordinate coordinate){
        service.moveProbeNextCoordinate(coordinate);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset")
    @Secured("ROLE_UPDATE_PROBE_MOVES")
    public ResponseEntity resetProbeLocation(@RequestBody @Valid ProbeLocation newLocation){
        service.resetProbeLocation(newLocation);
        return ResponseEntity.ok().build();
    }
}
