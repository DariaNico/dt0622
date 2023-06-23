package com.dt0622.thetoolrental.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dt0622.thetoolrental.model.Tool;

public interface ToolRepository extends CrudRepository<Tool, Long> {

  List<Tool> findByBrand(String brand);

  Tool findByToolCode(long toolCode);
}
