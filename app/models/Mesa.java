package models;

import java.awt.Color;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.*;
import play.*;
import play.data.validation.*;

@Entity
public class Mesa extends Model {

	@Required(message="Nombre is requiered")	
	public String nombre;
	@OneToOne
	public Usuario representante;
	@Match(value="#{1}[a-fA-F\\d]{6}")
	public String color;
	
	public String descripcion;
	
	public Mesa(String nombre, Usuario representante, String descripcion){
		this (nombre, representante, "#AAAAAA",descripcion);
	}
	
	public Mesa(String nombre, Usuario representante,
			String color, String descripcion){
		this.nombre = nombre;
		this.representante = representante;
		this.color = color;
		this.descripcion = descripcion;
	}
	
	public String toString(){
		return this.nombre;
	}
	
}
