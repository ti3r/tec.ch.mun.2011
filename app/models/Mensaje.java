package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import play.data.validation.Required;
import play.db.jpa.Model;
import controllers.CRUD.Hidden;

@Entity
public class Mensaje extends Model {
 
	@Required
	public String mensaje;
	@Hidden
	public Date fecha;
	@OneToOne
	public Usuario autor;
	
	public Imagen foto;
	
	@PrePersist
	public void setFecha(){
		this.fecha = new Date(System.currentTimeMillis());
	}
	
	public String toString(){
		return this.mensaje;
	}
}
