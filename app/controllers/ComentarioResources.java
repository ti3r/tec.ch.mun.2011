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
    		int pagina){
    	JPAQuery query = Comentario.find(" evento.id = ? " +
    			"order by fecha desc ", eventoId);
    	boolean moreFetch = (Comentario.count() > (pagina*5));
    	int pag = pagina;
    	List comentarios = query.fetch(++pag,5);
    	GetComentariosResult result = new GetComentariosResult();
    	result.comentarios = comentarios;
    	result.moreFetch = moreFetch;
    	//return the number of page that has been fetch
    	result.pagina = pag;
    	renderJSON(result);
    	
    }
}
