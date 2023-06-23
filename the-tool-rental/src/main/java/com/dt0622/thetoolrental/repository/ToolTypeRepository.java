package com.dt0622.thetoolrental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dt0622.thetoolrental.model.ToolType;

public interface ToolTypeRepository extends JpaRepository<ToolType, String> {

  List<ToolType> findByDailyCharge(float dailyCharge);
}