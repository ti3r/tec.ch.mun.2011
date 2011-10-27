package models;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import org.apache.commons.lang.StringEscapeUtils;

import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;
import play.mvc.results.RenderBinary;
import controllers.CRUD.Hidden;

@Entity
public class Mensaje extends Model {
 
	@Required
	public String mensaje;
	@Hidden
	public Date fecha;
	@OneToOne
	public Usuario autor;

	public Blob foto;
	
	@PrePersist
	public void setFecha(){
		//mensaje = StringEscapeUtils.escapeHtml(mensaje);
		this.fecha = new Date(System.currentTimeMillis());
	}
	
	public String getFecha(){
		if (fecha != null)
			return SimpleDateFormat.getInstance().format(fecha);
		else
			return "";
	}
	
	public String toString(){
		return this.mensaje;
	}
	
	
}
