package controllers;

import java.util.List;


import models.Comentario;
import models.GetComentariosResult;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.*;

public class ComentarioResources extends Controller {

    public static void comentariosList(){
    	JPAQuery q = Comentario.find("");
    	List comentarios = q.fetch(3);
    	renderJSON(comentarios);
    }

    public static void getComentarios(Long eventoId,
    		Integer pagina){
    	JPAQuery query = Comentario.find(" evento.id = ? " +
    			"order by fecha desc ", eventoId);
    	List comentarios = query.fetch(++pagina,5);
    	boolean moreFetch = (Comentario.count("evento.id = ?", eventoId) 
    			> (pagina*5));
    	GetComentariosResult result = new GetComentariosResult();
    	result.comentarios = comentarios;
    	result.mas = moreFetch;
    	//return the number of page that has been fetch
    	result.pagina = pagina;
    	renderJSON(result);
    	
    }
}
