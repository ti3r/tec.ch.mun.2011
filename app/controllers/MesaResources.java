package controllers;

import java.util.List;

import models.Mesa;
import play.mvc.*;
import models.Evento;

public class MesaResources extends Controller {

    public static void mesasList() {
        List mesas = Mesa.findAll();
        renderJSON(mesas);
    }

    public static void getMesa(Long id){
    	List mesas = Mesa.find("id = ?", id).fetch();
    	if (mesas != null && !mesas.isEmpty()){
    		Mesa m = (Mesa) mesas.get(0);
    		renderJSON(m);
    	}else{
    		notFound("Mesas not found for the given id. "+id);
    	}
    }
    
    public static void getMesaEventos(Long id){
    	List eventos = Evento.find("mesa.id = ? ",id).fetch();
    	if (eventos != null & !eventos.isEmpty()){
    		renderJSON(eventos);
    	}
    }
    
}
