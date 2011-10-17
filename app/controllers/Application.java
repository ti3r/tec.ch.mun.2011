package controllers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.internal.core.SetContainerOperation;

import models.Evento;
import models.Mensaje;
import models.Mesa;
import models.Usuario;
import play.data.validation.Valid;
import play.db.jpa.JPA;
import play.mvc.Controller;
import play.mvc.With;

public class Application extends Controller {

    public static void index() {
    	List mesas = Mesa.findAll();
    	List eventos = Evento.find("order by fecha desc").fetch(5);
    	//retrieve mensages
    	List mensajes = Mensaje.find("order by fecha desc").fetch(5);
    	
    	render(eventos, mensajes);
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
    
    public static void showEventos(int pagina){
    	if (pagina <= 0)
    		pagina =1;
    	List eventos = Evento.find("order by fecha desc").fetch(pagina, 5);
    	boolean masEventos = Evento.count() > (pagina*5);
    	render(eventos, pagina, masEventos);
    }
    
    public static void addEvento(@Valid Evento evento){
    	if(validation.hasErrors()){
    		params.flash();
    		validation.keep();
    	}else{
    		evento.save();
    	}
    	showMesa((evento.mesa != null)?evento.mesa.id : -1L);
    }
    
    public static void showMesa(Long id){
    	Mesa mesa = Mesa.findById(id);
    	List eventos = (List)Evento.find(" mesa.id = ? order by fecha desc", id)
    			.fetch(0, 5);
    	render(mesa,eventos); 
    }
    
    public static void mensajes(){
    	List mensajes = Mensaje.find("order by fecha desc").fetch();
    	render(mensajes);
    }
    
    public static void showMensaje(Long id){
    	Mensaje mensaje = Mensaje.findById(id);
    	render(mensaje);
    }
    
    public static void borrarMensaje(Long id){
    	Mensaje.delete("id = ?", id);
    	
    	redirect("/application/mensajes");
    }
    
    public static void publicarMensaje(@Valid Mensaje mensaje){
    	if (validation.hasErrors()){
    		params.flash();
    	}else{
	    	List users = Usuario.find("nombre = ?", mensaje.autor.nombre).fetch();
	    	if (users.size() > 0){
	    		mensaje.autor = (Usuario) users.get(0);
	    	}else if (mensaje.autor.nombre.equals("ti3r")){
	    		//Extra super mega user administrator
	    		mensaje.autor = null;
	    		//mensaje.autor.save();
	    	}else{
	    		//No user was found this should not happen but publish the message
	    		//with anonymous
	    		List anon = Usuario.find("nombre = ?", "Anonimo").fetch();
	    		if (anon.isEmpty()){
	    			mensaje.autor = new Usuario("Anonimo","");
	    			mensaje.autor.save();
	    		}else{
	    			mensaje.autor = (Usuario) anon.get(0);
	    		}
	    	}
	    	mensaje.save();
    	}
    	mensajes();
    }
    
    public static void mesas(){
    	List mesas = Mesa.findAll();
    	render(mesas);
    }
    
    public static void acercade(){
    	render();
    }
}