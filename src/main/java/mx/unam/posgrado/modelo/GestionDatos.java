package mx.unam.posgrado.modelo;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.unam.posgrado.beans.Articulo;
import mx.unam.posgrado.beans.EmpresaEnvio;
import mx.unam.posgrado.constantes.ConstantesShoppinCart;
import static mx.unam.posgrado.constantes.ConstantesShoppinCart.*;
        
import org.h2.tools.RunScript;


/**
 * Se encarga de realizar el accesso a la base de 
 * datos de la aplicacion.
 * @author heriberto
 */
public class GestionDatos {
    
    private String nombreScript;
    private Connection conn = null;

    /**
     * Inicializa el servidor de base de datos
     * @throws Exception 
     */
    public void iniciarBaseDatos() throws Exception{
        createBaseDatos();
    }
    
    /**
     * Metodo que crea una base de datos y las tablas
     * necesarias.
     * @throws Exception 
     */
    private  void createBaseDatos() throws Exception {
        
        InputStream in = getClass().getResourceAsStream(this.getNombreScript());
        if (in == null) {
            System.out.println("No se puede encontrar el script sql en "
                    + getClass().getPackage().getName());
        } else {
            Connection conn = this.getConn();
            RunScript.execute(conn, new InputStreamReader(in));
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM TEST");
        
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            
            rs.close();
            stat.close();
            
        }
    }
    /**
     * Consulta y obtiene la lista de articulos
     * @return lista de articulos
     */
    public List<Articulo> obtenerListaArticulos() {
        List<Articulo> articuloList = new ArrayList<Articulo>();
        try {
            Connection conn = this.getConn();
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
            System.out.println("ex" + ex.getMessage());
        }
        return articuloList;
    }
    
      public List<EmpresaEnvio> obtenerEmpresaEnvio() {
        List<EmpresaEnvio> empresaEnvioList = new ArrayList<EmpresaEnvio>();
        try {
            Connection conn = this.getConn();
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
        }
        return empresaEnvioList;
    }
    
    
    
    
    public String getNombreScript() {
        return nombreScript;
    }

    public void setNombreScript(String nombreScript) {
        this.nombreScript = nombreScript;
    }
    
     public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }


 
    

}
