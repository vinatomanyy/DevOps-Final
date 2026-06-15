package com.example.demo.controller;

import com.example.demo.model.TerrainImage;
import com.example.demo.repository.TerrainImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terrain-images")
@RequiredArgsConstructor
public class TerrainImageController {

    private final TerrainImageRepository terrainImageRepository;

    @GetMapping
    public List<TerrainImage> getAll() {
        return terrainImageRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TerrainImage> getById(@PathVariable Long id) {
        return terrainImageRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/terrain/{terrainId}")
    public List<TerrainImage> getByTerrain(@PathVariable Long terrainId) {
        return terrainImageRepository.findByTerrainId(terrainId);
    }

    @PostMapping
    public TerrainImage create(@RequestBody TerrainImage image) {
        return terrainImageRepository.save(image);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!terrainImageRepository.existsById(id)) return ResponseEntity.notFound().build();
        terrainImageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
