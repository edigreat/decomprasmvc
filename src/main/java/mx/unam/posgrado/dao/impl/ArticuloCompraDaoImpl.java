/**
 * 
 */
package mx.unam.posgrado.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.unam.posgrado.beans.Articulo;
import mx.unam.posgrado.beans.EmpresaEnvio;
import mx.unam.posgrado.constantes.ConstantesShoppinCart;
import mx.unam.posgrado.dao.ArticuloCompraDao;

/**
 * @author heriberto
 *
 */
@Repository
public class ArticuloCompraDaoImpl implements ArticuloCompraDao {

	private static final Logger logger = Logger.getLogger(ArticuloCompraDaoImpl.class);
	
	@Autowired
    private DataSource dataSource;
	
	
	/* (non-Javadoc)
	 * @see mx.unam.posgrado.dao.ArticuloCompraDao#obtenerListaArticulos()
	 */
	@Override
	public List<Articulo> obtenerListaArticulos() {
		 List<Articulo> articuloList = new ArrayList<Articulo>();
	        try {
	            Connection conn = this.dataSource.getConnection();
	            Statement stat = conn.createStatement();
	            ResultSet rs = stat.executeQuery(ConstantesShoppinCart.SELECT_ARTICULO);
	            while (rs.next()) {
	                Articulo articulo = new Articulo();
	                articulo.setIdentificador(rs.getBigDecimal("IDENTIFICADOR"));
	                articulo.setDescripcion(rs.getString("DESCRIPCION"));
	                articulo.setNombreImagen(rs.getString("NOMBREIMAGEN"));
	                articulo.setPrecio(rs.getDouble("PRECIO"));
	                articuloList.add(articulo);
	            }
	            rs.close();
	            stat.close();
	        } catch (SQLException ex) {
	        	logger.warn("Error al obtener la lista de articulos" + ex.getMessage());
	        }
	        return articuloList;
	}
	
	  public List<EmpresaEnvio> obtenerEmpresaEnvio() {
	        List<EmpresaEnvio> empresaEnvioList = new ArrayList<EmpresaEnvio>();
	        try {
	            Connection conn = this.dataSource.getConnection();
	            Statement stat = conn.createStatement();
	            ResultSet rs = stat.executeQuery(ConstantesShoppinCart.SELECT_EMPRESAENVIO);
	            while (rs.next()) {
	                EmpresaEnvio empresaEnvio = new EmpresaEnvio();
	                empresaEnvio.setIdentificador(rs.getBigDecimal("IDENTIFICADOR"));
	                empresaEnvio.setDescripcion(rs.getString("DESCRIPCION"));
	                empresaEnvio.setTarifa(rs.getDouble("TARIFA"));
	                empresaEnvioList.add(empresaEnvio);
	            }
	            rs.close();
	            stat.close();
	        } catch (SQLException ex) {
	            System.out.println("ex" + ex.getMessage());
	        	logger.warn("Error al obtener la lista de articulos" + ex.getMessage());

	        }
	        return empresaEnvioList;
	    }

}
