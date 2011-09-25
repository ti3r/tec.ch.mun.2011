package controllers;

import play.*;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.*;

import java.util.*;

import org.codehaus.groovy.runtime.typehandling.IntegerMath;

import models.*;

public class Application extends Controller {

    public static void index() {
    	//retrieve the Eventos in desc order
    	JPAQuery q = Evento.find("order by fecha desc");
    	List eventos = q.fetch(5);
    	boolean moreFetch = (Evento.count() > 5);
    	if (moreFetch){
	    	Integer page = 1;
    		render(eventos,moreFetch,page);
    	}
    	render(eventos);
    }
    
    public static void showEvento(Long id){
    	List eventos = (List) Evento.find(" id = ?",id).fetch();
    	if (eventos.size() > 1){
    		throw new IllegalStateException("More than one evento " +
    				"fetches the evento.id "+id);
    	}else{
    		Evento evento = (Evento) eventos.get(0);
    		String noMoreComentariosMsg = 
    				play.i18n.Messages.get("showEvento.noMoreComentariosMsg");
    		render(evento, noMoreComentariosMsg);
    	}
    }
    
    public static void addEvento(Long mesaId, String titulo, String desc){
    	Mesa m = Mesa.findById(1L);
    	if (m == null){
    		notFound("Mesa not found contact the admin");
    	}
    	Evento e = new Evento(m, titulo, desc);
    	e.save();
    	showMesa(1L);
    }
    
    public static void showMesa(Long id){
    	Mesa mesa = Mesa.findById(id);
    	List eventos = (List)Evento.find(" mesa.id = ? order by fecha desc", id)
    			.fetch(0, 5);
    	render(mesa,eventos); 
    }
    
}