
package mx.unam.posgrado.constantes;

/**
 * Clase que contiene las constantes para
 * el proyecto
 * @author heriberto
 */
public interface ConstantesShoppinCart {
    /**Consultas de catalogos**/
    static String SELECT_ARTICULO="SELECT IDENTIFICADOR, DESCRIPCION,NOMBREIMAGEN,"
            + "PRECIO FROM ARTICULO ORDER BY IDENTIFICADOR";
    static String SELECT_EMPRESAENVIO="SELECT IDENTIFICADOR, DESCRIPCION, "
            + " TARIFA FROM EMPRESAENVIO ORDER BY IDENTIFICADOR";
    /** Valores para impuestos **/
    static double IMPUESTO_01 = .02d ;  
    static double IMPUESTO_02 = .035d ; 
    static double IMPUESTO_03 = .04d ;
    /** Formato para fechas **/
    static String FORMATO_FECHA = "dd 'de' MMMM 'de' yyyy";
    static String FORMATO_HORA= "HH:mm:ss";
  
  

    
    /** Atributos de sesion **/
    static String SESION_CONTENEDOR_PURCHASE="purchaseContainer";
    static String SESION_LISTA_ARTICULOS="articuloList";
    static String SESION_LISTA_EMPRESAS_ENVIO="empresaEnvioList";
    static String SESION_CONTENEDOR_COMPRA="compraContainer";


    /** Atributos de request **/
    static String REQUEST_CONTENEDOR_ARTICULOS_ELEGIDOS="articuloElegidoContainer";
    
    /** Atributos de sesion **/
    static String SESION_LISTA_PRODUCTOS_SELECCIONADOS="purchases";
    static String SESION_LISTA_CATALOGO_PRODUCTOS="articulos";
    static String SESION_LISTA_CATALOGO_PAQUETERIA="empresaEnvioList";
    static String SESION_COMPRA_REALIZADA="compraContainer";
    static String SESION_IMPUESTO_CALCULADO="impuesto";
    static String SESION_COMPRA_TOTAL_CALCULADA="totalPurchase";
    static String SESION_COMPRA_TOTAL_CON_IMPUESTOS="totalPurchaseImpuesto";
    
    /**Nombre de las vistas TILES**/
    static String VISTA_CATALOGO_PRODUCTO="catalogo";
    static String VISTA_CHECKOUT_PRODUCTO="checkout";
    static String VISTA_COMPRA_PRODUCTO="compra";
   /** nombres de pagina html **/
    static String INDICADOR_REDIRECCION="redirect:";
    static String INDICADOR_FORWARD="forward:/";
    
    static String PAGINA_HTML_DE_COMPRAS="decompras.html";
    static String PAGINA_HTML_VERIFICA_CHECKOUT="verificaCheckout.html";
    static String PAGINA_HTML_CHECKOUT="checkout.html";
    static String PAGINA_HTML_REINICIO_COMPRA="reiniciar.html";

    static String PAGINA_HTML_REINICIO_PRINCIPAL="reiniciarInicio.html";
    static String PAGINA_HTML_CONFIRMA_COMRA="confirmPurchase.html";
    
    /**Nombre de las peticiones **/
    static String REQUEST_ADD_PRODUCTO="/addarticulo";
    static String REQUEST_UPDATE_PRODUCTO="/updateCar";
    static String REQUEST_CLEAR_PRODUCTO="/clear";

    
 

    
    
}
