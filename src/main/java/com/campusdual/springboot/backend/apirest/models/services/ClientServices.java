package com.campusdual.springboot.backend.apirest.models.services;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campusdual.springboot.backend.apirest.models.dao.IClientDao;
import com.campusdual.springboot.backend.apirest.models.entity.Client;

@Service          
public class ClientServices implements IClientService{

	@Autowired
	private IClientDao clientDao;
	
	@Override
	public List<Client> mostrarTodos() {
		return clientDao.findAll();
	}

	@Override
	public Client findById(Long id) {
		return clientDao.findById(id).orElse(null);
	}

	@Override
	public Client save(Client client) {
		return clientDao.save(client);
	}

	@Override
	public void delete(Long id) {
		clientDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly= true)
	public Page<Client> findAll(Pageable pageable) {
		return clientDao.findAll(pageable);
	}
	
	

}
