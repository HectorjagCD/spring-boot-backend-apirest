package com.campusdual.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
@Table(name="clientes")
public class Client implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="No puede estar vacío")
	@Column(nullable = false)
	@Size(min = 4, max = 12, message = "Mínimo 4 carácteres, máximo 12")
	private String name;
	
	@NotEmpty(message="No puede estar vacío")
	@Column(nullable = false)
	private String surname;
	
	
	@Column(name="create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	@PrePersist //Con esto fijamos un valor por defecto para el atributo
    public void prePersist() {
        createAt = new Date();
    }
	
	
	@Email(message = "No tiene el formato correcto")
	@NotEmpty(message="No puede estar vacío")
	@Column(nullable = false)// , unique = true
	private String email;
	
	
	//GETERS SETTERS
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 *  SE AUTOINCLUYE POR REQUERIMIENTO DEL FRAMEWORK
	 */
	private static final long serialVersionUID = 1L;
	
}
