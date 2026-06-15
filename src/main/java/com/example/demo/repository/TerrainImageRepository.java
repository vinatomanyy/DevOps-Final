package com.example.demo.repository;

import com.example.demo.model.TerrainImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TerrainImageRepository extends JpaRepository<TerrainImage, Long> {
    List<TerrainImage> findByTerrainId(Long terrainId);
}
