package mx.unam.posgrado.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArticuloElegidoContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4622334858846480851L;
	
	private List<Purchase> articulosElegidosList;
	/**
	 * Constructor publico
	 */
	public ArticuloElegidoContainer(){
		articulosElegidosList = new ArrayList<Purchase>();
	}

	

	@Override
	public String toString() {
		String resultado = "\n";
		for(Purchase purchase:this.articulosElegidosList){
			resultado+=purchase+"\n";
		}
		return "ArticuloElegidoContainer [articulosElegidosList="
				+ resultado + "]";
	}



	public List<Purchase> getArticulosElegidosList() {
		return articulosElegidosList;
	}



	public void setArticulosElegidosList(List<Purchase> articulosElegidosList) {
		this.articulosElegidosList = articulosElegidosList;
	}

}
