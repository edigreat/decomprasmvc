package mx.unam.posgrado.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Clase que representa la informacion
 * de un articulo seleccionado del 
 * catalogo de articulos.
 * @author heriberto
 */
public class Purchase implements Serializable,Cloneable {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 7382920032088696801L;
	
	private BigDecimal identificador;
    private String descripcion;
    private Double precio;
    private int cantidad;
    private String cantidadForma;
    private boolean habilitado;
    
    public Purchase(){}
    
    public Purchase(BigDecimal identificador, String descripcion,
			Double precio, int cantidad, String cantidadForma) {
		super();
		this.identificador = identificador;
		this.descripcion = descripcion;
		this.precio = precio;
		this.cantidad = cantidad;
		this.cantidadForma = cantidadForma;
		this.habilitado = false;
	}

	/**
     * Calcula el precio del total de piezas del articulo
     * @return
     */
    public double getTotal(){
        BigDecimal bd = new BigDecimal( cantidad * precio);
         bd = bd.setScale(3, RoundingMode.HALF_UP);
         return bd.doubleValue();
    }

    @Override
    public String toString() {
        return "Purchase{" + "identificador=" + identificador + ", descripcion=" + descripcion + ", precio=" 
        		+ precio + ", cantidad=" + cantidad +", cantidadForma="+cantidadForma+", habilitado="+habilitado+ '}';
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            // This should never happen
            throw new InternalError(e.toString());
        }
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((identificador == null) ? 0 : identificador.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Purchase)) {
			return false;
		}
		Purchase other = (Purchase) obj;
		if (identificador == null) {
			if (other.identificador != null) {
				return false;
			}
		} else if (!identificador.equals(other.identificador)) {
			return false;
		}
		return true;
	}

	public boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public String getCantidadForma() {
		return cantidadForma;
	}

	public void setCantidadForma(String cantidadForma) {
		this.cantidadForma = cantidadForma;
	}

	
    
    
    
}
