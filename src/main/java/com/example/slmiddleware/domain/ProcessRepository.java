package com.example.slmiddleware.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface ProcessRepository extends JpaRepository<Process, Long> {
}
