package com.example.demo.controller;

import com.example.demo.model.Terrain;
import com.example.demo.repository.TerrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terrains")
@RequiredArgsConstructor
public class TerrainController {

    private final TerrainRepository terrainRepository;

    @GetMapping
    public List<Terrain> getAll() {
        return terrainRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Terrain> getById(@PathVariable Long id) {
        return terrainRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Terrain create(@RequestBody Terrain terrain) {
        return terrainRepository.save(terrain);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Terrain> update(@PathVariable Long id, @RequestBody Terrain updated) {
        return terrainRepository.findById(id).map(t -> {
            updated.setId(id);
            return ResponseEntity.ok(terrainRepository.save(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!terrainRepository.existsById(id)) return ResponseEntity.notFound().build();
        terrainRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
