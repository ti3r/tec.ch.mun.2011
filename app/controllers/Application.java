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
    	JPAQuery q = Evento.find("order by fecha desc", null);
    	List eventos = q.fetch(5);
    	boolean moreFetch = (Evento.count() > 5);
    	if (moreFetch){
	    	Integer page = 1;
    		render(eventos,moreFetch,page);
    	}
    	render(eventos);
    }
    
    public static void showEvento(Long id){
    	List eventos = (List) Evento.find(" id = "+id, null).fetch();
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
    
    
    public static void showMesa(Long id){
    	Mesa mesa = Mesa.findById(id);
    	render(mesa);
    }
    
}