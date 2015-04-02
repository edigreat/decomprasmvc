
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
    /** Acciones para el control **/
    static String ACCION_AGREGAR="add";
    static String ACCION_CLEAR="clear";
    static String ACCION_ACTUALIZAR="update";
    static String ACCION_CHECKOUT="checkout";
    static String ACCION_CANCELAR="cancelar";
    static String ACCION_COMPRAR="compra";
    static String ACCION_REINICIAR="reiniciar";
    /**Nombre de Paginas **/
    static String PREFIX="/pages/";
    static String EXTENSION=".jsp";
    static String PAGINA_LISTADO=PREFIX+"listado"+EXTENSION;
    static String PAGINA_CHECKOUT=PREFIX+"checkout"+EXTENSION;
    static String PAGINA_COMPRA=PREFIX+"compra"+EXTENSION;
    /**Nombre de parametros**/
    static String PARAMETRO_OPERACION="operacion";
    static String PARAMETRO_PRODUCTO_SELECCIONADO="cantidad";
    static String PARAMETRO_PRODUCTO_BORRADO="articuloborrado";
    static String PARAMETRO_ERROR_CHECKOUT_VACIO="mensajeError";
    
    static String PARAMETRO_TARJETA_CREDITO="tarjetaCredito";
    static String PARAMETRO_NIP_CREDITO="nipCredito";
    static String PARAMETRO_CODIGO_TRANSACCION="codigoTransaccion";
    static String PARAMETRO_EMPRESA_ENVIO="empresaEnvioId";

    
    /** Atributos de sesion **/
    static String SESION_CONTENEDOR_PURCHASE="purchaseContainer";
    static String SESION_LISTA_ARTICULOS="articuloList";
    static String SESION_LISTA_EMPRESAS_ENVIO="empresaEnvioList";
    static String SESION_CONTENEDOR_COMPRA="compraContainer";


    /** Atributos de request **/
    static String REQUEST_CONTENEDOR_ARTICULOS_ELEGIDOS="articuloElegidoContainer";

    
    /**Nombre de las vistas**/
    static String VISTA_CATALOGO_PRODUCTO="catalogo";
    static String VISTA_CHECKOUT_PRODUCTO="checkout";
    static String VISTA_COMPRA_PRODUCTO="compra";

    static String SESION_LISTA_PRODUCTOS_SELECCIONADOS="purchases";
    static String SESION_LISTA_CATALOGO_PRODUCTOS="articulos";
    static String SESION_LISTA_CATALOGO_PAQUETERIA="empresaEnvioList";
    static String SESION_COMPRA_REALIZADA="compraContainer";
    static String SESION_IMPUESTO_CALCULADO="impuesto";
    static String SESION_COMPRA_TOTAL_CALCULADA="totalPurchase";
    static String SESION_COMPRA_TOTAL_CON_IMPUESTOS="totalPurchaseImpuesto";

    
    
    
    /**Mensajes de Error**/
    static String MENSAJE_ERROR_CHECKOUT_VACIO="Error: Todos los campos son necesarios";

    
}
