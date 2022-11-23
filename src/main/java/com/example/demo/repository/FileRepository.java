package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.FileDB;

public interface FileRepository extends JpaRepository<FileDB, String> {

}
