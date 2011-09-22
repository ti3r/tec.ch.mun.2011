package models;

import play.*;
import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Usuario extends Model {
    
	@Required
	public String nombre;
	@Required
	@Email
	public String correo;
	public Date alta;
		
	public Usuario(String nombre, String correo){
		this.nombre = nombre;
		this.correo = correo;
	}
	
	@PrePersist
	public void setAlta(){
		alta = new Date(System.currentTimeMillis());
	}
	
	public String toString(){
		return this.nombre;
	}
}
