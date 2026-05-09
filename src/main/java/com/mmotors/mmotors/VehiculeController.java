package com.mmotors.mmotors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vehicules")
public class VehiculeController {

    private VehiculeRepository vehiculeRepository;
    public VehiculeController(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    @GetMapping
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }
}
