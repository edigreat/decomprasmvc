package mx.unam.posgrado.beans;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Bean que representa la informacion 
 * de los articulos de la tienda
 * @author heriberto
 */
public class Articulo implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -2866473957112066840L;
	private BigDecimal identificador;
    private String descripcion;
    private String nombreImagen;
    private Double precio;
    private int cantidad;

    
    @Override
	public String toString() {
		return "Articulo [identificador=" + identificador + ", descripcion="
				+ descripcion + ", nombreImagen=" + nombreImagen + ", precio="
				+ precio + ", cantidad=" + cantidad + "]";
	}

	public int getCantidad(){
        return this.cantidad;
    }
    
    public void setCantidad(int cantidad){
        this.cantidad=cantidad;
    }

    public BigDecimal getIdentificador() {
        return identificador;
    }

    public void setIdentificador(BigDecimal identificador) {
        this.identificador = identificador;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    
    

}
