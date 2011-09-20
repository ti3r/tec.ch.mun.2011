package models;

import play.*;
import play.data.validation.Email;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Comentario extends Model {
    
	@Required
	@ManyToOne
	public Evento evento;
	@Required
	@MaxSize(10000)
	public String comentario;
	
	public String autor;
	@Email
	public String contacto;
	public Date fecha;
	
	public Comentario(Evento evento, String comentario){
		this(evento, comentario, null);
	}

	public Comentario(Evento evento, String comentario, 
			String autor) {
		this(evento,comentario,autor,null);
	}
	
	
	public Comentario(Evento evento, String comentario, 
			String autor, String contacto) {
		this.evento = evento;
		this.comentario = comentario;
		this.autor = autor;
		this.contacto = contacto;
	}
	
	@PrePersist
	public void setFecha(){
		fecha = new Date(System.currentTimeMillis());
	}
	
	public String toString(){
		return this.comentario;
	}
}
