package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
    	//retrieve the Eventos in desc order
    	List eventos = Evento.find("order by fecha desc", null).fetch();
    	render(eventos);
    }

    public static void showEvento(Long id){
    	List eventos = (List) Evento.find(" id = "+id, null).fetch();
    	if (eventos.size() > 1){
    		throw new IllegalStateException("More than one evento " +
    				"fetches the evento.id "+id);
    	}else{
    		Evento evento = (Evento) eventos.get(0);
    		render(evento);
    	}
    }
}