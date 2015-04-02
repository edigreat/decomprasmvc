package mx.unam.posgrado.dao;

import java.util.List;

import mx.unam.posgrado.beans.Articulo;
import mx.unam.posgrado.beans.EmpresaEnvio;

public interface ArticuloCompraDao {
	
	
	List<Articulo> obtenerListaArticulos() ;
	
	List<EmpresaEnvio> obtenerEmpresaEnvio();

}
