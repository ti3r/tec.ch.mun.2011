package controllers;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import models.Evento;
import models.MoreEventosResult;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.*;

public class EventoResources extends Controller {
	
	public static void eventosList() {
		JPAQuery q = Evento.find("");
		List eventos = q.fetch(5);
		renderJSON(eventos);
	}

	public static void getEvento(Long id) {
		List eventos = Evento.find("id = ?", id).fetch();
		if (eventos != null && !eventos.isEmpty()) {
			Evento e = (Evento) eventos.get(0);
			renderJSON(e);
		}
	}

	public static void getMoreEventos(int lastPage) {
		JPAQuery q = Evento.find("order by fecha desc", null);
		int page = lastPage+1;
		List eventos = q.fetch(page, 5);
		MoreEventosResult result = new MoreEventosResult();
		result.eventos = eventos;
		result.moreFetch = (Evento.count() > (page*5));
		result.page = page;
		//if (moreFetch) {
			renderJSON(result);
		//}
		//render(eventos);
	}

}
