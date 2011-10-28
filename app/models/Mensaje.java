package models;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import org.apache.commons.lang.StringEscapeUtils;

import com.sun.xml.internal.ws.util.ByteArrayBuffer;

import play.data.validation.MaxSize;
import play.data.validation.MinSize;
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
		if (fecha != null)
			return SimpleDateFormat.getInstance().format(fecha);
		else
			return "";
	}
	
	public String toString(){
		return this.mensaje;
	}
	
	
}
