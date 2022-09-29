package com.campusdual.springboot.backend.apirest.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.campusdual.springboot.backend.apirest.models.entity.Client;

public interface IClientService {
	
	public List<Client> mostrarTodos();
	
	public Client findById(Long id);

	public Client save (Client client);
	
	public void delete (Long id);
	
	public Page<Client> findAll(Pageable pageable); 
	
}
