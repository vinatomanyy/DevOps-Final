package com.example.demo.repository;

import com.example.demo.model.Terrain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TerrainRepository extends JpaRepository<Terrain, Long> {
    List<Terrain> findByIsAvailableTrue();
    List<Terrain> findByOwnerId(Long ownerId);
}
