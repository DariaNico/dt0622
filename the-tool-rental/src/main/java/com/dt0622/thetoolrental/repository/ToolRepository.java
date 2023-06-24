package com.dt0622.thetoolrental.repository;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import com.dt0622.thetoolrental.model.Tool;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<Tool, String> {
  Optional<Tool> findByToolCode(String toolCode);

  List<Tool> findByToolTypeId(String toolTypeId);

  List<Tool> findByBrand(String brand);

  @Transactional
  void deleteByToolTypeId(String toolTypeId);
}