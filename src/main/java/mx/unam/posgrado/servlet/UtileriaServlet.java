package mx.unam.posgrado.servlet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import mx.unam.posgrado.beans.Articulo;
import mx.unam.posgrado.beans.CompraContainer;
import mx.unam.posgrado.beans.EmpresaEnvio;
import mx.unam.posgrado.beans.Purchase;
import mx.unam.posgrado.constantes.ConstantesShoppinCart;
import static mx.unam.posgrado.constantes.ConstantesShoppinCart.*;
import mx.unam.posgrado.modelo.GestionDatos;

/**
 * Clase que contiene metodos de utileria para el 
 * controlador
 * @author heriberto
 */
public class UtileriaServlet {
    
    
    /**
     * Realiza la consulta de articulos y la coloca en sesion
     * @param request
     * @param gestionDatos 
     */
    public void getArticulosList(HttpServletRequest request,
            GestionDatos gestionDatos){
         List<Articulo> articuloList =  (List<Articulo>) 
                 request.getSession().getAttribute(
                         ConstantesShoppinCart.SESION_LISTA_CATALOGO_PRODUCTOS);
          if(articuloList == null){
              System.out.println("Cargando articulos");
                request.getSession().setAttribute(
                        ConstantesShoppinCart.SESION_LISTA_CATALOGO_PRODUCTOS,
                   gestionDatos.obtenerListaArticulos());
          }
     
    }
    
    /**
     * Metodo que buscar el articulo por id
     * @param idArticulo
     * @param request
     * @return 
     */
   private Articulo buscarArticuloCatalogo(int idArticulo,HttpServletRequest request){
   
    List<Articulo> articuloList = (List<Articulo>) 
                 request.getSession().getAttribute(ConstantesShoppinCart.SESION_LISTA_CATALOGO_PRODUCTOS);
    Articulo articuloBuscado=null;
    for(Articulo articulo:articuloList){
        if(articulo.getIdentificador().equals(new BigDecimal(idArticulo)))
        {
            articuloBuscado = articulo;
        
        }
    
    }
   
    return articuloBuscado;
   }
    
    
    
     /**
     * Metodo para agregar un purchase a la lista de sesion
     * @param request
     * @param idArticulo
     * @param cantidad 
     */
    public void addPurchaseList(HttpServletRequest request,
            String cantidadArticulo ,int cantidad){
        
        System.out.println("cantidadArticulo: ["+cantidadArticulo+"]");
        int idArticulo = Integer.valueOf(cantidadArticulo.split("_")[1]);
        boolean purchaseExist=false;
        List<Purchase> purchaseList =  (List<Purchase>) 
                request.getSession().
                        getAttribute(ConstantesShoppinCart.SESION_LISTA_PRODUCTOS_SELECCIONADOS);
        if(purchaseList == null){
              System.out.println("Creando purchase");
              purchaseList =new ArrayList<Purchase>();
                request.getSession().
                        setAttribute(
                                ConstantesShoppinCart.SESION_LISTA_PRODUCTOS_SELECCIONADOS,
                        purchaseList);
          }
         Articulo articuloAgregado=buscarArticuloCatalogo(idArticulo,request);
         for(Purchase purchaseFind:purchaseList){
                if(purchaseFind.getIdentificador().equals(articuloAgregado.getIdentificador())){
                   purchaseFind.setCantidad(purchaseFind.getCantidad()+cantidad);
                   purchaseExist = true;
                   break; 
                }
                
            }
         
         if(!purchaseExist){
             Purchase nuevoPurchase = new Purchase();
             nuevoPurchase.setIdentificador(articuloAgregado.getIdentificador());
             nuevoPurchase.setCantidad(cantidad);
             nuevoPurchase.setDescripcion(articuloAgregado.getDescripcion());
             nuevoPurchase.setPrecio(articuloAgregado.getPrecio());
             ((List<Purchase>) request.getSession().
                     getAttribute(
                             ConstantesShoppinCart.SESION_LISTA_PRODUCTOS_SELECCIONADOS)
                                ).add(nuevoPurchase);
             System.out.println("Purchase agregado");
         }

    }
    
    /**
     * Borra un elemento de la lista de purchases
     * @param request
     * @param idArticulo 
     */
    public void deletePurchaseList(HttpServletRequest request,String articuloBorrado){
    
        List<Purchase> purchaseList =  (List<Purchase>) 
                                    request.getSession().getAttribute(
                                    ConstantesShoppinCart.SESION_LISTA_PRODUCTOS_SELECCIONADOS);
        System.out.println("articuloBorrado: ["+articuloBorrado+"]");
        int idArticulo = Integer.valueOf(articuloBorrado.split("_")[1]);
        int i = 0;
        for(Purchase purchase:purchaseList){
            if(purchase.getIdentificador().equals(new BigDecimal(idArticulo))){
                System.out.println("Id a Borrar "+ idArticulo);
                break;
            }
            i=i+1;
        }
        
        purchaseList.remove(i);
    }
     
    /**
     * Metodo para obtener la lista de articulos 
     * @param request 
     */
    public void getEmpresaEnvioList(HttpServletRequest request,
            GestionDatos gestionDatos){
         List<EmpresaEnvio> empresaEnvioList =  (List<EmpresaEnvio>)
                 request.getSession().getAttribute(
                         ConstantesShoppinCart.SESION_LISTA_CATALOGO_PAQUETERIA);
          if(empresaEnvioList == null){
              System.out.println("Cargando empresas de envio");
                request.getSession().setAttribute(
                        ConstantesShoppinCart.SESION_LISTA_CATALOGO_PAQUETERIA,
                   gestionDatos.obtenerEmpresaEnvio());
          }
     
    }
    
    /**
     * Calcula el total de la compra
     * @param request 
     */
    public void calcularTotal(HttpServletRequest request){
        
        List<Purchase> purchaseList =  (List<Purchase>) 
                request.getSession().getAttribute(
                        ConstantesShoppinCart.SESION_LISTA_PRODUCTOS_SELECCIONADOS);
        double total=0;
        if(purchaseList!=null){
            for(Purchase purchase:purchaseList){
                total = total + purchase.getTotal();
            }
        
        }
        BigDecimal totalConRedondeno = new BigDecimal(total);
        totalConRedondeno = totalConRedondeno.setScale(3, RoundingMode.HALF_UP);
        request.getSession().setAttribute(ConstantesShoppinCart.SESION_COMPRA_TOTAL_CALCULADA,
                totalConRedondeno.doubleValue());
    
    }
    
    /**
     * Obtiene el impuesto a aplicar de acuerdo a el total
     * @param total
     * @return impuesto
     */
    public void obtenerImpuesto(HttpServletRequest request){
           double impuesto=0;
           double total =(double) request.getSession().getAttribute(
                   ConstantesShoppinCart.SESION_COMPRA_TOTAL_CALCULADA);

           if ( total <= 3567.00 ){
               impuesto = ConstantesShoppinCart.IMPUESTO_01;
           } 
           if ( total > 3567.00 && total <= 5678.00 ){
                impuesto = ConstantesShoppinCart.IMPUESTO_02;

           }
            if ( total > 5678.00 ){
                impuesto = ConstantesShoppinCart.IMPUESTO_03;

            }
        BigDecimal totalConImpuesto = new BigDecimal(total + (total*impuesto));
        totalConImpuesto = totalConImpuesto.setScale(3, RoundingMode.HALF_UP);
        request.getSession().setAttribute(ConstantesShoppinCart.SESION_IMPUESTO_CALCULADO,impuesto);
        request.getSession().setAttribute(
                                        ConstantesShoppinCart.SESION_COMPRA_TOTAL_CON_IMPUESTOS,
                                        (totalConImpuesto.doubleValue()));

    
    }
    
    /**
     * Remueve todos los elementos de sesion 
     * que tienen informacion sobre la compra
     * @param request 
     */
    public void cancelarCompra(HttpServletRequest request){
        request.getSession().setAttribute(ConstantesShoppinCart.SESION_IMPUESTO_CALCULADO,0);
        request.getSession().setAttribute(ConstantesShoppinCart.SESION_COMPRA_TOTAL_CON_IMPUESTOS,0);
        request.getSession().setAttribute(ConstantesShoppinCart.SESION_COMPRA_TOTAL_CALCULADA,0);
         ((List<Purchase>) request.getSession().getAttribute
        (ConstantesShoppinCart.SESION_LISTA_PRODUCTOS_SELECCIONADOS)).clear();
        request.getSession().setAttribute(ConstantesShoppinCart.SESION_COMPRA_REALIZADA, null);


    }
    /**
     * Busca una empresa de envio por id y la coloca 
     * en la sesion.
     * @param request
     * @param identificador 
     */
    private EmpresaEnvio obtenerEmpresaEnvioById(HttpServletRequest request,
                                                int identificador){
        EmpresaEnvio empresaEnvio = null;
        List<EmpresaEnvio> empresaEnvioList = 
                (List<EmpresaEnvio>) request.getSession().getAttribute
                (ConstantesShoppinCart.SESION_LISTA_CATALOGO_PAQUETERIA);
        
        for(EmpresaEnvio empresaEnvioSearch:empresaEnvioList){
            if(empresaEnvioSearch.getIdentificador().equals(new BigDecimal(identificador))){
                empresaEnvio = empresaEnvioSearch;
                break;
            }
        
        }
        return empresaEnvio;

    }
    
    
    /**
     * Verifica si la compra se puede realizar
     * @param request
     * @return 
     */
    public boolean realizarCompra(HttpServletRequest request){
        boolean isSuccess=false;
        String numeroTarjeta =  request.getParameter(ConstantesShoppinCart.PARAMETRO_TARJETA_CREDITO);
        String nipTarjeta =     request.getParameter(ConstantesShoppinCart.PARAMETRO_NIP_CREDITO);
        String codigoTransaccion = request.getParameter(ConstantesShoppinCart.PARAMETRO_CODIGO_TRANSACCION);
        String idEmpresaEnvio = request.getParameter(ConstantesShoppinCart.PARAMETRO_EMPRESA_ENVIO);
        System.out.println("Confirmando compra Tarjeta["+numeroTarjeta+"]["+nipTarjeta+"]["+codigoTransaccion+"]");
        System.out.println("Empresa de envio seleccionada : " + idEmpresaEnvio);
        if(numeroTarjeta!=null && nipTarjeta!= null && codigoTransaccion!=null &&
                !numeroTarjeta.isEmpty() && !nipTarjeta.isEmpty() && 
                !codigoTransaccion.isEmpty() && idEmpresaEnvio!=null)
        {
        isSuccess=true;
            Calendar fechaActual = Calendar.getInstance();
            SimpleDateFormat simpleDateFormatFecha = 
            new SimpleDateFormat(ConstantesShoppinCart.FORMATO_FECHA,new Locale("es"));
            SimpleDateFormat simpleDateFormatHora = 
            new SimpleDateFormat(ConstantesShoppinCart.FORMATO_HORA,new Locale("es"));
            
            EmpresaEnvio empresaEnvio = obtenerEmpresaEnvioById(request,Integer.parseInt(idEmpresaEnvio));
            CompraContainer compraContainer = new CompraContainer();
            compraContainer.setTarjetaCredito(numeroTarjeta);
            compraContainer.setNipCredito(nipTarjeta);
            compraContainer.setCodigoTransaccion(codigoTransaccion);
            compraContainer.setEmpresaEnvio(empresaEnvio);
            //compraContainer.setFechaActual(simpleDateFormatFecha.format(fechaActual.getTime()));
            //compraContainer.setHoraActual(simpleDateFormatHora.format(fechaActual.getTime()));
            request.getSession().setAttribute(ConstantesShoppinCart.SESION_COMPRA_REALIZADA, compraContainer);
        }
        else{
        if(request.getSession().getAttribute(ConstantesShoppinCart.SESION_COMPRA_REALIZADA)!=null){
            isSuccess=true;
        }
        
        
        }
        
        
        return isSuccess;
    
    }
    
}
