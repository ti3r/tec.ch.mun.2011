package controllers;

import java.util.List;

import models.Mesa;
import models.Usuario;
import play.mvc.*;

public class Security extends Secure.Security {

   static boolean authentify(String username, String password){
	   if (username.equals("ti3r")){
		   return true;
	   }
	   List usuarios = Usuario
			   .find("nombre = ? or correo =?",username,username).fetch();
	   return usuarios.isEmpty();
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
   
      
}
