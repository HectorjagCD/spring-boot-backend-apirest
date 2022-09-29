package com.campusdual.springboot.backend.apirest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.campusdual.springboot.backend.apirest.models.entity.Client;
import com.campusdual.springboot.backend.apirest.models.services.IClientService;
//import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api") // la ruta del control en la vbase de datos ip + puerto + /api
public class ClientRestController {

	@Autowired
	private IClientService clientService;

	@GetMapping("/clients")
	public List<Client> index() {
		return clientService.mostrarTodos();
	}

	@GetMapping("/clients/{id}")
	public Client show(@PathVariable Long id) {
		return clientService.findById(id);

	}

	@PostMapping("/clients")
//	@ResponseStatus(HttpStatus.CREATED) // Con nuevo metodos obsoleto
	public ResponseEntity<?> create(@Valid @RequestBody Client client, BindingResult result) {
		Client clienteNuevo = new Client();
		ResponseEntity<Map<String, Object>> rest = null;
		Map<String, Object> response = new HashMap<>();

		if (!result.hasErrors()) {

			try {
				clienteNuevo = clientService.save(client);
				response.put("message", "Cliente creado con éxito.");
				response.put("Cliente", clienteNuevo);
				rest = new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

			} catch (DataAccessException e) {
				response.put("message", "No se ha podido crear correctamente");
				response.put("Error", e.getMostSpecificCause().getMessage());
				rest = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

			}

		} else {

			// Método 1: for implícito
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			// Método 2: for explícito
			List<String> errors2 = new ArrayList<>();
			for (FieldError err : result.getFieldErrors()) {
				errors2.add(err.getField() + " - " + err.getDefaultMessage());
			}

			response.put("errors", errors); // Ó response.put("errors", errors2);
			rest = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return rest;
	}

	@PutMapping("/clients/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@Valid @RequestBody Client client, BindingResult result, @PathVariable Long id) {
		Map<String, Object> response = new HashMap<String, Object>();
		ResponseEntity<Map<String, Object>> responseEntity = null;
		Client oldClient = clientService.findById(id);
		Client nClient = new Client();

		if (oldClient != null) {

			if (!result.hasErrors()) {

				try {
					if (!client.getName().isEmpty()) {
						oldClient.setName(client.getName());
					}
					if (!client.getSurname().isEmpty()) {
						oldClient.setSurname(client.getSurname());
					}
					if (!client.getEmail().isEmpty()) {
						oldClient.setEmail(client.getEmail());
					}

					nClient = clientService.save(oldClient);
					response.put("message", "Cliente se ha modificado con éxito");
					response.put("Cliente", nClient);
					responseEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
				} catch (DataAccessException dae) {
					response.put("message", "No se ha podido modificar el usuario");
					response.put("error", dae.getMostSpecificCause().getMessage());
					responseEntity = new ResponseEntity<Map<String, Object>>(response,
							HttpStatus.INTERNAL_SERVER_ERROR);
				}

			} else {

				List<String> errors = result.getFieldErrors().stream()
						.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
						.collect(Collectors.toList());
				response.put("errors", errors);
				responseEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

			}

		} else {

			response.put("message", "No existe el usuario");
			responseEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;

	}

	@DeleteMapping("/clients/{id}")
	// @ResponseStatus(HttpStatus.NO_CONTENT) // Lo gestionamos ahora con el
	// ResponseEntity
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		ResponseEntity<Map<String, Object>> rest = null;
		try {
			clientService.delete(id);
			response.put("mensaje", "Cliente " + id + " borrado con éxito");
			rest = new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "No se ha borrado con éxito");
			response.put("error", e.getMostSpecificCause().getMessage());
			rest = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return rest;
	}
	
	
	@GetMapping("/clients/page/{page}")
	public Page<Client> index(@PathVariable Integer page){
		Pageable pageable = PageRequest.of(page, 4);
		return clientService.findAll(pageable);
	}
	

}
