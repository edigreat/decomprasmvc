package mx.unam.posgrado.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.unam.posgrado.beans.Purchase;
import mx.unam.posgrado.constantes.ConstantesShoppinCart;
import mx.unam.posgrado.modelo.GestionDatos;

/**
 * Clase principal que realiza las funciones de 
 * controlador en el modelo MVC
 * 
 * @author heriberto
 */
public class Control extends HttpServlet {
    
    private GestionDatos gestionDatos;
    private UtileriaServlet utileriaServlet;
    /**
     * Inicia la aplicacion y la base de datos
     * @param config
     * @throws ServletException 
     */
    public void init(ServletConfig config) throws ServletException {
        System.out.println("Iniciando aplicacion ");
        super.init(config);
        ServletContext ctx = this.getServletContext();
        String nombreScript =  ctx.getInitParameter("scriptSql");
        gestionDatos = new GestionDatos();
        utileriaServlet =  new UtileriaServlet();
        gestionDatos.setNombreScript(nombreScript.trim());
        gestionDatos.setConn((Connection)obtenerContexto().getAttribute("connection"));
        try {
            gestionDatos.iniciarBaseDatos();
        } catch (Exception ex) {
            System.out.println("Error al iniciar la Base de datos" + ex.getMessage());
        }
        

        System.out.println("Finalizando inicio de aplicacion");

    }
    
    
    /**
     * Metodo que procesa todas las peticiones realizadas
     * al servlet
     * @param request peticion
     * @param response respuesta
     * @throws ServletException
     * @throws IOException 
     */
    protected void procesaPeticion(HttpServletRequest request, 
                        HttpServletResponse response)
                        throws ServletException, IOException
    {
        System.out.println("Entrando a la peticion"); 
        String operacion=request.getParameter(ConstantesShoppinCart.PARAMETRO_OPERACION);
        
        if(operacion!=null){
            System.out.println("Operacion: " + operacion);
        }
        RequestDispatcher requestDispatcher;

        if( operacion == null ) {
           utileriaServlet.getArticulosList(request,gestionDatos);   
           utileriaServlet.calcularTotal(request);
           requestDispatcher = request.getRequestDispatcher(ConstantesShoppinCart.PAGINA_LISTADO);
           requestDispatcher.forward(request,response);
        }
        
      else
        {
            if(operacion.equals(ConstantesShoppinCart.ACCION_AGREGAR)){
            Map<String, String[]> parameters = request.getParameterMap();
            for(String parameter : parameters.keySet()){
                if(parameter.toLowerCase().startsWith(
                        ConstantesShoppinCart.PARAMETRO_PRODUCTO_SELECCIONADO)) 
                {
                    String[] values = parameters.get(parameter);
                    for(int i=0 ; i < values.length;i++)
                    {
                        if(Integer.valueOf(values[i])!=0){
                            utileriaServlet.addPurchaseList(request,parameter,Integer.valueOf(values[i]));
                        }
                    }
                }
            }
            utileriaServlet.calcularTotal(request);
            requestDispatcher = request.getRequestDispatcher(ConstantesShoppinCart.PAGINA_LISTADO);
            requestDispatcher.forward(request,response);
            }
            
            if(operacion.equals(ConstantesShoppinCart.ACCION_CLEAR)){
                List<Purchase> purchaseList = 
                        (List<Purchase>) request.getSession().getAttribute
                                        (ConstantesShoppinCart.SESION_LISTA_PRODUCTOS_SELECCIONADOS);
                if(purchaseList != null){ purchaseList.clear();}
                utileriaServlet.calcularTotal(request);
                requestDispatcher = request.getRequestDispatcher(ConstantesShoppinCart.PAGINA_LISTADO);
                requestDispatcher.forward(request,response);
            }
            
            if(operacion.equals(ConstantesShoppinCart.ACCION_ACTUALIZAR)){
                Map<String, String[]> parameters = request.getParameterMap();
                for (String parameter : parameters.keySet()) {
                    if (parameter.toLowerCase().startsWith(ConstantesShoppinCart.PARAMETRO_PRODUCTO_BORRADO)) {
                         String[] values = parameters.get(parameter);
                        for(int i=0 ; i < values.length;i++)
                        {
                            utileriaServlet.deletePurchaseList(request, parameter);
    
                        }
                    }
                }
                utileriaServlet.calcularTotal(request);
                requestDispatcher = request.getRequestDispatcher(ConstantesShoppinCart.PAGINA_LISTADO);
                requestDispatcher.forward(request,response);
            }
            
            if(operacion.equals(ConstantesShoppinCart.ACCION_CHECKOUT)){
                List<Purchase> purchaseList =  
                        (List<Purchase>) request.getSession().getAttribute
                                (ConstantesShoppinCart.SESION_LISTA_PRODUCTOS_SELECCIONADOS);
                if(purchaseList==null || purchaseList.isEmpty()){
                     requestDispatcher = request.getRequestDispatcher(ConstantesShoppinCart.PAGINA_LISTADO);
                     requestDispatcher.forward(request,response);
                }
                else{
                    utileriaServlet.getEmpresaEnvioList(request,gestionDatos);
                    utileriaServlet.obtenerImpuesto(request);
                    requestDispatcher = request.getRequestDispatcher(ConstantesShoppinCart.PAGINA_CHECKOUT);
                    requestDispatcher.forward(request,response);
                }
            }
            
             if(operacion.equals(ConstantesShoppinCart.ACCION_CANCELAR)){
                 utileriaServlet.cancelarCompra(request);
                requestDispatcher = request.getRequestDispatcher(ConstantesShoppinCart.PAGINA_LISTADO);
                requestDispatcher.forward(request,response);
            }
             if(operacion.equals(ConstantesShoppinCart.ACCION_COMPRAR)){
                boolean isSuccess = utileriaServlet.realizarCompra(request);
                if(isSuccess){
                    requestDispatcher = request.getRequestDispatcher(ConstantesShoppinCart.PAGINA_COMPRA);
                    requestDispatcher.forward(request,response);
                }
                else{
                    request.setAttribute(ConstantesShoppinCart.PARAMETRO_ERROR_CHECKOUT_VACIO,
                                            ConstantesShoppinCart.MENSAJE_ERROR_CHECKOUT_VACIO);
                    requestDispatcher = request.getRequestDispatcher(ConstantesShoppinCart.PAGINA_CHECKOUT);
                    requestDispatcher.forward(request,response);
                }
            }
            if(operacion.equals(ConstantesShoppinCart.ACCION_REINICIAR)){
                utileriaServlet.cancelarCompra(request);
                requestDispatcher = request.getRequestDispatcher(ConstantesShoppinCart.PAGINA_LISTADO);
                requestDispatcher.forward(request,response);
            } 
        
        }
    }
    
    private ServletContext obtenerContexto(){
        return this.getServletContext();
    }
    
    
    /**
     * Metodo que acepta las peticiones por metodo get
     * @param request peticion
     * @param response respuesta
     * @throws ServletException
     * @throws IOException 
     */
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response)
                        throws ServletException, IOException
    {
        procesaPeticion(request,response);
    }
    
    /**
     * Metodo que acepta las peticiones por metodo post
     * @param request peticion
     * @param response respuesta
     * @throws ServletException
     * @throws IOException 
     */
    protected void doPost(HttpServletRequest request, 
                        HttpServletResponse response)
                        throws ServletException, IOException
    {
        procesaPeticion(request,response);
    }
    
}
