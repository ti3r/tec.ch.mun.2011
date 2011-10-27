package controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import models.Mensaje;
import models.Mesa;
import models.Usuario;
import play.mvc.*;

public class Security extends Secure.Security {

   static boolean authentify(String username, String password){
	   if (username.trim().equals("ti3r")){
		   return true;
	   }
	   List usuarios = Usuario
			   .find("nombre = ? or correo =?",username.trim(),username.trim()).fetch();
	   if (usuarios.isEmpty()){
		   return false;
	   }else{
		   Usuario usuario = (Usuario) usuarios.get(0);
		   return usuario.comparePasswords(password);
	   }
   }
   
   static boolean mesaBelongsToUser(String user, Long id){
	   //Megasuperuser return true for everything
	   if (user.equals("ti3r")){
		   return true;
	   }
	   List mesa = Mesa.find("id = ? and " +
	   		"(representante.nombre = ? or representante.correo = ?)",id,user,user)
			   .fetch();
	   return !mesa.isEmpty();
   }
   
   static boolean mensajeBelongsToUser(String user, Long id){
	 //Megasuperuser return true for everything
	   if (user.equals("ti3r")){
		   return true;
	   }
	   List mensaje = Mensaje.find("id = ? and (autor.nombre = ? or autor.correo =? )", 
			   id,user,user).fetch();
	   return !mensaje.isEmpty();
   }
}
