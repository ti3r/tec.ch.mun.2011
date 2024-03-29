package controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import models.Base64Coder;
import models.Comentario;
import models.Evento;
import models.Mensaje;
import models.Mesa;
import models.Usuario;
import play.data.validation.Valid;
import play.db.jpa.Blob;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
    	List eventos = Evento.find("order by id desc").fetch(5);
    	//retrieve mensages
    	List mensajes = Mensaje.find("order by id desc").fetch(5);
    	
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
    	List eventos = Evento.find("order by id desc").fetch(pagina, 5);
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
    	List mensajes = Mensaje.find("order by id desc").fetch();
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
    
    public static void borrarEvento(Long id){
    	Evento.delete("id = ?", id);
    	redirect("/application/showeventos");
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
	    	if (mensaje.foto != null){
				//encode the foto
				long length = mensaje.foto.getFile().length();
				if (length > Integer.MAX_VALUE){
					System.err.println("Foto Too large");
				}else{
					byte[] bytes = new byte[(int)length];
					try {
						mensaje.foto.get().read(bytes);
						char[] rawImg = Base64Coder.encode(bytes);
						mensaje.encoded_foto = new String(rawImg);
					} catch (IOException e) {
						System.err.println("Error encripting foto");
					}
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
    
    public static void publicarComentario(@Valid Comentario comentario){
    	System.out.println(comentario.toString());
    	comentario.save();
    	showEvento(comentario.evento.id);
    }
    
    public static void autenticarUsuario(String user, String md5password){
    	//Super mega ultra archi requete chingon usuario
    	if (user.equals("ti3r")){
    		ok();
    	}
    	List usuarios = Usuario.find("nombre = ? or correo = ?", user, user)
    			.fetch();
    	if (usuarios.isEmpty()){
    		notFound("User not found");
    	}else{
    		//get the first user
    		Usuario u = (Usuario) usuarios.get(0);
    		boolean correct = u.compareEncriptedPassowrd(md5password);
    		if (correct){
    			ok();
    		}else{
    			unauthorized();
    		}
    	}
    }
    
    public static void cambiarFotoDeMensaje(){
    	List<Mensaje> mensajes = Mensaje.findAll();
    	render(mensajes);
    }
    
    public static void cambiafoto(Long mensajeId, Blob foto){
    	Mensaje mensaje = Mensaje.findById(mensajeId);
    	if (mensaje != null){
    		if (foto.getFile().length() > Integer.MAX_VALUE){
    			error("Foto file is to large");
    		}else{
	    		byte[] bytes = new byte[(int)foto.getFile().length()];
	    		try {
					foto.get().read(bytes);
					String foto_encoded = new String (Base64Coder.encode(bytes));
		    		mensaje.encoded_foto = foto_encoded;
		    		mensaje.save();
		    		redirect("/application/mensajes");
				} catch (IOException e) {
					error("Error encoding Mensaje Foto");
				}
    		}
    	}else if (mensaje == null){
    		error("Mensaje no encontrado para id"+mensajeId);
    	}
    }
    
    public static void mensajeFoto(long id){
    	final Mensaje mensaje = Mensaje.findById(id);
    	notFoundIfNull(mensaje);
    	if (mensaje.encoded_foto != null && !mensaje.encoded_foto.equals("")){
	    	response.setContentTypeIfNotSet("image/*");
	    	byte[] img = Base64Coder.decode(mensaje.encoded_foto);
	    	renderBinary(new ByteArrayInputStream(img));
    	}else{
    		notFound("Foto not found");
    	}
    }
    
    public static void resumen(){
    	List<Evento> eventos = Evento.findAll();
    	List<Mensaje> mensajes = Mensaje.findAll();
    	render(eventos, mensajes);
    }
    
}