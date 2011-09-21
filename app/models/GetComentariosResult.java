package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

public class GetComentariosResult extends Model {
    
	public List comentarios = null;
	public boolean mas = false;
	public int pagina = 0;
}
