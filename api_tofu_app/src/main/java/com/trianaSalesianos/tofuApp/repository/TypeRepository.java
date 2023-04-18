package com.trianaSalesianos.tofuApp.repository;

import com.trianaSalesianos.tofuApp.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface TypeRepository extends JpaRepository<Type, UUID>, JpaSpecificationExecutor<Type> {
}
