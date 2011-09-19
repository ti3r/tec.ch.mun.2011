package models;

import play.*;
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
	public String contacto;
	
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
	
	
	public String toString(){
		return this.comentario;
	}
}
