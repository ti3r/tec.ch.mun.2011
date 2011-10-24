package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;

import com.sun.mail.iap.ByteArray;

import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Usuario extends Model {
    
	@Required
	@Column(unique=true)
	public String nombre;
	@Required
	@Email
	@Column(unique=true)
	public String correo;
	public Date alta;
	public String pass;
		
	public Usuario(String nombre, String correo){
		this.nombre = nombre;
		this.correo = correo;
	}
	
	@PrePersist
	public void prePresist(){
		alta = new Date(System.currentTimeMillis());
		try {
			this.pass = new String( 
					MessageDigest.getInstance("MD5").digest(this.pass.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			//Pasword will be stored in plain text. Fix this.
		}
	}
	
	public boolean compareEncriptedPassowrd(String md5Password){
		return pass.equals(md5Password);
	}
	
	public boolean comparePasswords(String password){
		//Build the MD5 parameter
		try {
			String pass = new String(
			   MessageDigest.getInstance("MD5").digest(password.getBytes()));
			compareEncriptedPassowrd(pass);
		} catch (NoSuchAlgorithmException e) {
			//Something wrong happened log error and compare plain passwords
			e.printStackTrace();
		}
		return this.pass.equals(password);
	}
	
	public String toString(){
		return this.nombre;
	}
		
}
