package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Chat;

public interface ChatRepository extends JpaRepository<Chat,Integer>{
	List<Chat> findById(int id);
}
