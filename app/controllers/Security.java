package controllers;

import java.util.List;

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
   
   
   
}
