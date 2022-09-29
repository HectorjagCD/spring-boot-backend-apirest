package com.campusdual.springboot.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.campusdual.springboot.backend.apirest.models.entity.Client;

public interface IClientDao extends JpaRepository<Client, Long>{

}
