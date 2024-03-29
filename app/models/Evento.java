package models;

import play.*;
import play.data.validation.Email;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Evento extends Model {
    
	@ManyToOne
	@Required
	public Mesa mesa;
	@Required
	public String titulo;
	@Lob
	@MaxSize(5000)
	public String descripcion;
	
	public Date fecha;
	
	public Evento(Mesa mesa, String titulo, 
			String descripcion){
		this.mesa = mesa;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fecha = new Date();
	}
	
	public String toString(){
		return this.titulo;
	}
	
	public String getFecha(){
		if (fecha != null){
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Chihuahua"));
			calendar.setTime(fecha);
			return SimpleDateFormat.getInstance().format(calendar.getTime());
		}else{
			return "";
		}
	}
	
	@PrePersist
	public void setFechaToNow(){
		this.fecha = new Date(System.currentTimeMillis());
	}
	
}
