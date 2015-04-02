// ShoppingCart.java

package De_Compras ;

import java.io.* ;
import javax.servlet.ServletException ;
import javax.servlet.http.* ;
import java.util.Date ;
import java.text.SimpleDateFormat ;

public class ShoppingCart extends HttpServlet {

    String [] items = new String [] { "Bote con 3 Pelotas de Tenis", 
                                      "Bat de Baseball de madera deBeer W1610LL",
                                      "Porterías y balón de FootBall rápido", 
                                      "Velero de madera de balsa" } ;

    String [] imagenes = new String[] { "tenis.gif",
                                        "bat.gif",
                                        "porterias.gif",
                                        "velero.gif" } ;                                        

    float [] Precio = new float[] { 12.95f, 36.50f, 35.00f, 15.45f } ;
    float Imp1 = .02f ;  // 2%
    float Imp2 = .035f ; // 3.5%
    float Imp3 = .04f ;  // 4%
    
    float DHL = .04f ;  // 4%
    float UPS = .06f ;  // 6%
    float MEX_POST = .08f ;  // 8%
    
    public void doPost( HttpServletRequest request, HttpServletResponse response )
      throws IOException, ServletException {

        doGet( request, response );
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response )
      throws ServletException, IOException {

        response.setContentType( "text/html" );
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        int [] purchases = (int [])session.getValue( "purchases" );
        if ( purchases == null ) {
            purchases = new int [ items.length ];   // Cantidades de Artículos
            session.putValue( "purchases", purchases );
          }        
        if ( request.getParameter( "compra" ) != null )           
           	Compra( out, purchases, request ) ;  
        else { 
          out.println( "<html>" ) ;
          out.println( "<head><title>Shopping Cart</title></head>" ) ;
          out.println( "<body BGCOLOR=\"#FFFF99\"><p>" );                       
          if ( request.getParameter( "checkout" ) != null &&
              ! EmptyCar( purchases ) )           
           	Checkout( out, purchases, request.getRequestURI() ) ;           
          else {
               if ( request.getParameter( "add" ) != null ) 
                    addPurchases( request, purchases );                 
               else 
                 if ( request.getParameter( "clear" ) != null ||
                      request.getParameter( "cancelar" ) != null )  
                      for( int i=0; i<purchases.length; i++ )
                          purchases[ i ] = 0;                   
                 else 
                 if ( request.getParameter( "update" ) != null )	
                     for( int i=0; i<items.length; i++ )  
                     	if ( purchases[ i ] != 0 )
                               if ( (String) request.getParameter( "Ch" + items[ i ]  ) != null )
                                  purchases[ i ] = 0;       
               doForm( out, purchases, request.getRequestURI() );
               showPurchases( out, purchases ) ; 
              }
          }
        out.close( );
    }

    void addPurchases( HttpServletRequest request, int [] purchases ) {

        for ( int i=0; i<items.length; i++ ) {
            String added = (String)request.getParameter( items[ i ] );
            if ( !added.equals( "" ) )
                purchases[ i ] += Integer.parseInt( added );
        }
    }

    void doForm( PrintWriter out, int [] purchases, String requestURI ) {

        out.println( "<form method=POST action="+ requestURI +">" );
        out.println( "<h1><center><i>Almacenes Deportivos</i></center></h1>" );                          
        out.println( "<h2><center>Selecciona tus Artículos de Temporada !</center></h2>" );
        out.println( "<TABLE BORDER=1 ALIGN=CENTER>\n" +
                     "<TR BGCOLOR=\"#FFAD00\">\n" +
                     "<TH> <TH>Artículo<TH>Precio<TH>Cantidad" );

        for( int i=0; i < items.length; i++ )
           out.println( "<TR BGCOLOR=\"#FFFF00\"><TD BGCOLOR=\"#FFFFFF\" ALIGN=\"CENTER\"><img src=\"/servlets-examples/" + imagenes[i] + 
                        "\"><TD ALIGN=\"CENTER\">" + items[i] + "<TD>$ " + Float.toString( Precio[i] ) + "<TD><input name=\"" + 
                        items[ i ] + "\"value=0 size=3 align=\"right\">" );
        out.println( "</TABLE>" );
        out.println( "<p><center><input type=submit name=add value=\"Add To Cart\">&nbsp&nbsp \n"
                              + "<input type=submit name=checkout value=\"Check Out\">&nbsp&nbsp \n"
                              + "<input type=submit name=clear value=\"Clear Cart\">&nbsp&nbsp \n"
                              + "<input type=submit name=update value=\"Update Cart\">"
                      + "</center>" ) ; 
        out.println( "<hr><h2>Tu Carrito de Compras :</h2>" );                      
    }

    void showPurchases( PrintWriter out, int [] purchases ) 
        throws IOException {

        float Subtotal = 0.0f ;
        float Total = 0.0f ;
        String ColEliminar ;
        
        out.println( "<TABLE BORDER=1 ALIGN=CENTER>\n" +
                     "<TR BGCOLOR=\"#FFAD00\">\n" +
                     "<TH>Artículo<TH>Precio<TH>Cantidad<TH>Eliminar<TH>Total" );        

        for( int i=0; i<items.length; i++ )
           { 
             if ( purchases[ i ] != 0 )
                {
                  Subtotal = Precio[i] * purchases[ i ] ;                	                 
                  out.println( "<TR BGCOLOR=\"#FFFF00\"><TD>" + items[i] + "<TD>$ " + Float.toString( Precio[i] ) + "<TD ALIGN=\"RIGHT\">" + 
                               Integer.toString( purchases[ i ] ) + "<TD ALIGN=\"CENTER\"> <input type=\"checkbox\" name=\"" + "Ch" + items[i] + 
                               "\" value=\"" + "Checked" + "\">" + "<TD>$ " + Float.toString( Subtotal ) );                                      
                  Total = Total + Subtotal ;
                 }
          }
         out.println( "<TR BGCOLOR=\"#FFFF00\"><TD>" + " " + "<TD>" + " " + "<TD>" + " "+ "<TD>" +
                      "Total : " + "<TD>$ " + Float.toString( Total ) );                                  
         out.println( "</TABLE>" );
         out.println( "<br>" ); 
         out.println( "<h5><center>IMPORTANTE: Considera un 12% más sobre el costo total por impuestos y gastos de envío</center></h5>" );                      
         out.println( "</form>" ) ;
         out.println( "</body>" ) ;
         out.println( "</Html>" ) ;                
    }
    
  void Checkout( PrintWriter out, int [] purchases, String requestURI )  
      throws IOException {
      	
       float TotalConImp = 0.0f ;
       float Subtotal = 0.0f ;
       float Total = 0.0f ;
       out.println( "<form method=POST action="+ requestURI +">" );
       out.println( "<h1><center><i>Almacenes Deportivos</i></center></h1>" );                          
       out.println( "<h2><center>Compra de Artículos</center></h2>" );
       out.println( "<TABLE BORDER=1 ALIGN=CENTER>\n" +
                    "<TR BGCOLOR=\"#FFAD00\">\n" +
                    "<TH>Artículo<TH>Precio<TH>Cantidad<TH>Total" );
       for( int i=0; i<items.length; i++ )
          { 
            if ( purchases[ i ] != 0 )
               {
                 Subtotal = Precio[i] * purchases[ i ] ;                	                 
                 out.println( "<TR BGCOLOR=\"#FFFF00\"><TD>" + items[i] + "<TD>$ " + Float.toString( Precio[i] ) + "<TD ALIGN=\"RIGHT\">" + 
                              Integer.toString( purchases[ i ] ) + 
                              "<TD>$ " + Float.toString( Subtotal ) );                 
                 Total = Total + Subtotal ;
                }
          }
       out.println( "<TR BGCOLOR=\"#FFFF00\"><TD>" + " " + "<TD>" + " " + "<TD>" +
                    "Total : " + "<TD>$ " + Float.toString( Total ) );
       if ( Total <= 3567.00 )
            {
              out.println( "<TR BGCOLOR=\"#FFFF00\"><TD>" + " " + "<TD>" + " " + "<TD>" +
                           "Impuesto : " + "<TD>$ " + Float.toString( Total * Imp1 ) );                
              TotalConImp = Total + Total * Imp1 ; 
              out.println( "<TR BGCOLOR=\"#FFFF00\"><TD>" + " " + "<TD>" + " " + "<TD>" +
                           "Total : " + "<TD>$ " + Float.toString( TotalConImp ) );                
            }                                
       else
         if ( Total > 3567.00 && Total <= 5678.00 )
              {
                 out.println( "<TR BGCOLOR=\"#FFFF00\"><TD>" + " " + "<TD>" + " " + "<TD>" +
                              "Impuesto : " + "<TD>$ " + Float.toString( Total * Imp2 ) );                
                 TotalConImp = Total + Total * Imp2 ;                                
                 out.println( "<TR BGCOLOR=\"#FFFF00\"><TD>" + " " + "<TD>" + " " + "<TD>" +
                              "Total : " + "<TD>$ " + Float.toString( TotalConImp) );                                  	                  	
               }
         else
           if ( Total > 5678.00 )
                {
                   out.println( "<TR BGCOLOR=\"#FFFF00\"><TD>" + " " + "<TD>" + " " + "<TD>" +
                                "Impuesto : " + "<TD>$ " + Float.toString( Total * Imp3 ) );
                   TotalConImp = Total + Total * Imp3 ;                            
                   out.println( "<TR BGCOLOR=\"#FFFF00\"><TD>" + " " + "<TD>" + " " + "<TD>" +
                                "Total : " + "<TD>$ " + Float.toString( TotalConImp) );                                  	                  	                                        	
                }                              
           out.println( "<TR BGCOLOR=\"#FFAD00\"><TH ALIGN=\"CENTER\">Empresa de Envío <TH ALIGN=\"CENTER\">Importe <TH ALIGN=\"CENTER\">Elegir <TH ALIGN=\"CENTER\">Total" ); 
           out.println( "<TR BGCOLOR=\"#FFFF00\"><TD> DHL Envío en 48 Hrs. <TD>" + Float.toString( TotalConImp * DHL ) + 
                        "<TD ALIGN=\"CENTER\"><input type=\"radio\" name=\"Envio\" checked value=\"DHL\"> <TD>" + Float.toString( TotalConImp + TotalConImp * DHL ) + "\n" +
                        "<TR BGCOLOR=\"#FFFF00\"><TD> UPS Envío en 24 Hrs. <TD>" + Float.toString( TotalConImp * UPS )  + 
                        "<TD ALIGN=\"CENTER\"><input type=\"radio\" name=\"Envio\" value=\"UPS\"> <TD>" + Float.toString( TotalConImp + TotalConImp * UPS ) + "\n" +
                        "<TR BGCOLOR=\"#FFFF00\"><TD> MEX POST Envío en 12 Hrs. <TD>" + Float.toString( TotalConImp * MEX_POST ) + 
                        "<TD ALIGN=\"CENTER\"><input type=\"radio\" name=\"Envio\" value=\"MexPost\"> <TD>" +  Float.toString( TotalConImp + TotalConImp * MEX_POST ) ) ;           	
           out.println( "</TABLE>" );
           out.println( "<br>" ); 
           out.println( "Número de Tarjeta de Crédito: &nbsp&nbsp <input name=\"TarjCred\" value=\" \" size=16 align=\"right\"><br>" ); 
           out.println( "NIP de Tarjeta de Crédito: &nbsp&nbsp <input name=\"NIP\" value=\" \" size=4 align=\"right\"><br>" );            
           out.println( "Código de Transacción: &nbsp&nbsp <input name=\"Cod\" value=\" \" size=3 align=\"right\"><br>" );                       
           out.println( "<p><center><input type=submit name=cancelar value=\"Cancelar\">&nbsp&nbsp \n" +
                        "<input type=submit name=compra value=\"Realizar Compra\">" +
                        "</center>" ) ; 
           out.println( "</form>" ) ;
           out.println( "</body>" ) ;
           out.println( "</Html>" ) ;                
    }
     
     void Compra( PrintWriter out, int [] purchases, HttpServletRequest request ) 
          throws IOException {

        float TotalConImp = 0.0f ;
        float Subtotal = 0.0f ;
        float Total = 0.0f ;

        Date theDate ;
        String Meses[] = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" };
        String patron ;
        String fecha ;
        SimpleDateFormat formato ;          	

        patron = "dd/MM/yyyy";
        formato = new SimpleDateFormat( patron ) ;
        theDate = new Date() ;
        fecha = formato.format( theDate ) ;

        out.println( "<html>" ) ;
        out.println( "<head><title>Shopping Cart</title></head>" ) ;
        out.println( "<body><p>" );                       
        out.println( "<h1><center><i>Almacenes Deportivos</i></center></h1>" );                          
        out.println( "<h2><center>Comprobante de Compra de Artículos</center></h2>" );
        out.println( "Fecha: " + fecha.substring(0, 2).trim() + " de " +
                      Meses[ Integer.parseInt( fecha.substring(3, 5).trim() ) - 1 ] +
                      " del " + fecha.substring(6, 10).trim() + ".<br>" ) ;
        out.println( "Hora: " + theDate.toString().substring( 11, 20 ) + "<br>" ) ;
        out.println( "Número de Pedido: " + NumePedido() + "<br>" ) ;
        out.println( "Número de Guía de Pedido: " + NumeroGuia() + "<br>" ) ;          
        out.println( "Número de Tarjeta de Crédito: " + (String)request.getParameter( "TarjCred" ) + "<br><br>" ) ;                  
        
        out.println( "<TABLE BORDER=0 ALIGN=CENTER>\n" +
                     "<TR>\n" +
                     "<TH>Artículo<TH>Precio<TH>Cantidad<TH>Total" );
        for( int i=0; i<items.length; i++ )
           { 
             if ( purchases[ i ] != 0 )
                {
                  Subtotal = Precio[i] * purchases[ i ] ;                	                 
                     out.println( "<TR><TD>" + items[i] + "<TD>$ " + Float.toString( Precio[i] ) + "<TD ALIGN=\"RIGHT\">" + 
                                  Integer.toString( purchases[ i ] ) + "&nbsp&nbsp" +
                                  "<TD>$ " + Float.toString( Subtotal ) );                 
                  Total = Total + Subtotal ;
                 }
          }
        out.println( "<TR><TD>" + " " + "<TD>" + " " + "<TD><b>" +
                     "Total :" + "<TD>$ " + Float.toString( Total ) );
        if ( Total <= 3567.00 )
           {
              out.println( "<TR><TD>" + " " + "<TD>" + " " + "<TD><b>" +
                           "Impuesto :" + "<TD>$ " + Float.toString( Total * Imp1 ) );                
              TotalConImp = Total + Total * Imp1 ; 
              out.println( "<TR><TD>" + " " + "<TD>" + " " + "<TD><b>" +
                           "Total :" + "<TD>$ " + Float.toString( TotalConImp ) );                
            }                                
        else
          if ( Total > 3567.00 && Total <= 5678.00 )
             {
                out.println( "<TR><TD>" + " " + "<TD>" + " " + "<TD><b>" +
                             "Impuesto :" + "<TD>$ " + Float.toString( Total * Imp2 ) );                
                TotalConImp = Total + Total * Imp2 ;                                
                out.println( "<TR><TD>" + " " + "<TD>" + " " + "<TD><b>" +
                             "Total :" + "<TD>$ " + Float.toString( TotalConImp) );                                  	                  	
             }
          else
            if ( Total > 5678.00 )
               {
                  out.println( "<TR><TD>" + " " + "<TD>" + " " + "<TD><b>" +
                               "Impuesto : " + "<TD>$ " + Float.toString( Total * Imp3 ) );
                  TotalConImp = Total + Total * Imp3 ;                            
                  out.println( "<TR><TD>" + " " + "<TD>" + " " + "<TD><b>" +
                               "Total :" + "<TD>$ " + Float.toString( TotalConImp) );                                  	                  	                                        	
               }                                 
            if ( ((String)request.getParameter( "Envio" )).equals( "DHL" ) ) {            	
           	    out.println( "<TR><TH ALIGN=\"CENTER\">Empresa de Envío" ) ;  
           	    out.println( "<TR><TD> DHL Envío en 48 Hrs. <TD> <TD><b>Importe :<TD>$ " + Float.toString( TotalConImp * DHL ) ); 
        	    out.println( "<TR><TD> <TD> <TD><b>Total :<TD>$ " + Float.toString( TotalConImp + TotalConImp * DHL ) ); 
                out.println( "</TABLE><br>" );
                out.println( "<h4><center>Puedes consultar en la siguiente página el estado de envío de tu compra:</center></h4>" ); 
                out.println( "<h4><center><BLOCKQUOTE><A HREF=\"http://dhl.com//\"><PRE>http://dhl.com/</PRE></A></center></h4></BLOCKQUOTE>" ) ;
              }
            if ( ((String)request.getParameter( "Envio" )).equals( "UPS" ) ) {              
           	    out.println( "<TR><TH ALIGN=\"CENTER\">Empresa de Envío" ) ;
                out.println( "<TR><TD> UPS Envío en 24 Hrs. <TD> <TD><b>Importe :<TD>$ " + Float.toString( TotalConImp * UPS ) ); 
          	    out.println( "<TR><TD> <TD> <TD><b>Total :<TD>$ " + Float.toString( TotalConImp + TotalConImp * UPS ) ); 
                out.println( "</TABLE><br>" );
                out.println( "<h4><center>Puedes consultar en la siguiente página el estado de envío de tu compra:</center><h4>" ); 
                out.println( "<h4><center><BLOCKQUOTE><A HREF=\"http://ups.com//\"><PRE>http://ups.com/</PRE></A></center></h4></BLOCKQUOTE>" ) ;                              
             }
            if ( ((String)request.getParameter( "Envio" )).equals( "MexPost" ) ) {                           
           	    out.println( "<TR><TH ALIGN=\"CENTER\">Empresa de Envío" ) ; 
           	    out.println( "<TR><TD> MEX POST Envío en 12 Hrs. <TD> <TD><b>Importe :<TD>$ " + Float.toString( TotalConImp * MEX_POST ) ); 
           	    out.println( "<TR><TD> <TD> <TD><b>Total :<TD>$ " + Float.toString( TotalConImp + TotalConImp * MEX_POST ) ); 
                out.println( "</TABLE><br>" );
                out.println( "<h4><center>Puedes consultar en la siguiente página el estado de envío de tu compra:</center><h4>" ); 
                out.println( "<h4><center><BLOCKQUOTE><A HREF=\"http://mexpost.com//\"><PRE>http://mexpost.com/</PRE></A></center></h4></BLOCKQUOTE>" ) ;                              
             } 
            out.println( "<br>" );           
            out.println( "<h1><center><i>Gracias por tu compra !<i></center></h1>" );          	
            out.println( "</body>" ) ;
            out.println( "</Html>" ) ;                          	
          }
          
     boolean EmptyCar( int [] purchases ){
     	
        for( int i=0; i<items.length; i++ )         
          if ( purchases[ i ] != 0 )
            return ( false ) ;
        return( true ) ;                	
     } 
     
     String NumePedido() {
     	
     	return "67293AWT" ;
      }

     String NumeroGuia() {
     	
     	return "FA45CX78"  ;
      }           
}
