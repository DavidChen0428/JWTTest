package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ChatRepository;
import com.example.demo.model.Chat;
import com.example.demo.service.ChatService;
@Service
public class ChatServiceImpl implements ChatService{
	@Autowired
	ChatRepository chatrepository;
	
	@Override
	public void addChat(Chat chat) {
		chatrepository.save(chat);
	}

	@Override
	public List<Chat> allChat() {
		return chatrepository.findAll();
	}

	@Override
	public List<Chat> selectChatById(int id) {
		return chatrepository.findById(id);
	}

	@Override
	public void updateChat(Chat chat) {
		chatrepository.save(chat);
	}

	@Override
	public void deleteChatById(int id) {
		chatrepository.deleteById(id);
		
	}

}
