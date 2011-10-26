package controllers;

import java.util.Date;
import java.util.List;

import models.Mensaje;
import play.mvc.Controller;

public class MensajesResources extends Controller {

	
	public static void getMensajes(){
		List<Mensaje> mensajes = Mensaje.all().fetch();
		renderJSON(mensajes);
	}
	
	public static void areThereNewMensajes(long fecha){
		Date pDate = new Date(fecha);
		List<Mensaje> mensajes = Mensaje.find("fecha >= ? ", pDate).fetch();
		if (mensajes.size() > 0){
			ok();
		}else{
			notFound();
		}
	}
	
	public static void fetchNewMensajes(long fecha){
		Date pDate = new Date(fecha);
		List<Mensaje> mensajes = Mensaje.find("fecha >= ?", pDate).fetch();
		if (mensajes != null){
			renderJSON(mensajes);
		}else{
			error("Mensajes returned null");
		}
	}
	
}
