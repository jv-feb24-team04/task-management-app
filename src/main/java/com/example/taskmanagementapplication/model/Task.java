package com.example.taskmanagementapplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Task {
    @Id
    private Long id;
}
