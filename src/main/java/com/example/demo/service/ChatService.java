package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Chat;

public interface ChatService {
	
	// create
	void addChat(Chat chat);
	
	// read
	List<Chat> allChat();
	List<Chat> selectChatById(int id);
	
	// update
	void updateChat(Chat chat);
	
	// delete
	void deleteChatById(int id);
	
}
