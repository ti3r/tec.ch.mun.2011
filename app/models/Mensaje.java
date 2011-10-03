package models;

import play.*;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;

import controllers.CRUD.Hidden;

import java.util.*;

@Entity
public class Mensaje extends Model {
 
	@Required
	public String mensaje;
	@Hidden
	public Date fecha;
	@OneToOne
	public Usuario autor;
	
	@PrePersist
	public void setFecha(){
		this.fecha = new Date(System.currentTimeMillis());
	}
	
	public String toString(){
		return this.mensaje;
	}
}
