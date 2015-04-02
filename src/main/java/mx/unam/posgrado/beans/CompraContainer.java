package mx.unam.posgrado.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import mx.unam.posgrado.constantes.ConstantesShoppinCart;

/**
 * Clase que representa la informacion
 * de la compra realizada.
 * @author heriberto
 */
public class CompraContainer implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -6801190846024311576L;
	private EmpresaEnvio empresaEnvio;
    private String tarjetaCredito;
    private String nipCredito;
    private String codigoTransaccion;
   
    private Calendar calendar = Calendar.getInstance();

    
    public String obtenDescripcionEmpresa(){
        return empresaEnvio.getDescripcion();
    }
    
    public String getFechaActual() {
    	 SimpleDateFormat simpleDateFormatFecha = 
    	            new SimpleDateFormat(ConstantesShoppinCart.FORMATO_FECHA,new Locale("es"));
        return simpleDateFormatFecha.format(calendar.getTime());
    }

    public String getHoraActual() {
    	SimpleDateFormat simpleDateFormatHora = 
                new SimpleDateFormat(ConstantesShoppinCart.FORMATO_HORA,new Locale("es"));
        return simpleDateFormatHora.format(calendar.getTime());
    }

  
    

    public EmpresaEnvio getEmpresaEnvio() {
        return empresaEnvio;
    }

    public void setEmpresaEnvio(EmpresaEnvio empresaEnvio) {
        this.empresaEnvio = empresaEnvio;
    }

   
    public String getTarjetaCredito() {
        return tarjetaCredito;
    }

    public void setTarjetaCredito(String tarjetaCredito) {
        this.tarjetaCredito = tarjetaCredito;
    }

    public String getNipCredito() {
        return nipCredito;
    }

    public void setNipCredito(String nipCredito) {
        this.nipCredito = nipCredito;
    }

    public String getCodigoTransaccion() {
        return codigoTransaccion;
    }

    public void setCodigoTransaccion(String codigoTransaccion) {
        this.codigoTransaccion = codigoTransaccion;
    }

	

	@Override
	public String toString() {
		return "CompraContainer [empresaEnvio=" + empresaEnvio
				+ ", tarjetaCredito=" + tarjetaCredito + ", nipCredito="
				+ nipCredito + ", codigoTransaccion=" + codigoTransaccion
				+ ", fechaActual=" + getFechaActual() + ", horaActual=" + getHoraActual()
				+ " ]";
	}
    
    
    
    
    
    
    
}
