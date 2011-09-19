package models;

import java.awt.Color;

import javax.persistence.Entity;

import play.db.jpa.Model;
import play.db.jpa.*;
import play.*;
import play.data.validation.*;

@Entity
public class Mesa extends Model {

	@Required(message="Nombre is requiered")	
	public String nombre;
	@Email
	public String representante;
	public String color;
	
	public Mesa(String nombre, String representante){
		this (nombre, representante, "#AAAAAA");
	}
	
	public Mesa(String nombre, String representante,
			String color){
		this.nombre = nombre;
		this.representante = representante;
		this.color = color;
	}
	
	public String toString(){
		return this.nombre;
	}
	
}
