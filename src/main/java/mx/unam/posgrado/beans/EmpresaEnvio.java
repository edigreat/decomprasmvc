package mx.unam.posgrado.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Clase que representa la informacion
 * de las empresas de envio.
 * @author heriberto
 */
public class EmpresaEnvio implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 7308267760899175353L;
	private BigDecimal identificador;
    private String descripcion;
    private double tarifa;

    
    public double getTotal(double total){
        BigDecimal totalImpuesto = new BigDecimal(total + (this.tarifa * total));
        totalImpuesto = totalImpuesto.setScale(3, RoundingMode.HALF_UP);
        return totalImpuesto.doubleValue();
    }
    
    public double getImporte(double total){
        BigDecimal totalImpuesto = new BigDecimal(this.tarifa * total);
        totalImpuesto = totalImpuesto.setScale(3, RoundingMode.HALF_UP);
        return totalImpuesto.doubleValue();
    }

    public String getTarifaCadena(){
    	return String.valueOf(this.tarifa);
    }
    
    public void setTarifaCadena(String valor){
    	this.tarifa= Double.valueOf(valor);
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

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
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
		if (!(obj instanceof EmpresaEnvio)) {
			return false;
		}
		EmpresaEnvio other = (EmpresaEnvio) obj;
		if (identificador == null) {
			if (other.identificador != null) {
				return false;
			}
		} else if (!identificador.equals(other.identificador)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "EmpresaEnvio [identificador=" + identificador
				+ ", descripcion=" + descripcion + ", tarifa=" + tarifa + "]";
	}
    
    
    

}
