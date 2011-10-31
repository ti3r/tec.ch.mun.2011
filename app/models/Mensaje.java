package models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Blob;
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
	@Transient
	public Blob foto;
	
	@Column(columnDefinition="TEXT")
	@MinSize(Integer.MAX_VALUE)
	public String encoded_foto;
	
	@PrePersist
	public void setFecha(){
		//mensaje = StringEscapeUtils.escapeHtml(mensaje);
		this.fecha = new Date(System.currentTimeMillis());
	}
	
	public String getFecha(){
		if (fecha != null){
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Chihuahua"));
			calendar.setTime(fecha);
			return SimpleDateFormat.getInstance().format(calendar.getTime());
		}else
			return "";
	}
	
	public String toString(){
		return this.mensaje;
	}
	
	
}
