package com.example.slmiddleware.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

// Process 객체에 대한 CRUD 작업을 수행하는 JpaRepository 상속
public interface ProcessRepository extends JpaRepository<Process, Long> {
}
