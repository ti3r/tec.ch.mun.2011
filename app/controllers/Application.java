package controllers;

import java.util.ArrayList;
import java.util.List;

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
    	List eventos = new ArrayList<Evento>();
    	for(int i=0; i < mesas.size(); i++){
    		List list = Evento.find("Select e from Evento e where e.mesa.id = ? order by e.fecha ",
    				((Mesa)mesas.get(i)).getId()).fetch(1);
    		if (!list.isEmpty()){
    			Evento e = (Evento) list.get(0); 
    			eventos.add(e);
    		}
    	}
    	
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
    	Evento.find("order by evento.fecha").fetch(pagina, 5);
    	render();
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
    	List mensajes = Mensaje.findAll();
    	render(mensajes);
    }
    
    public static void publicarMensaje(Mensaje mensaje){
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
    	mensajes();
    }
    
}