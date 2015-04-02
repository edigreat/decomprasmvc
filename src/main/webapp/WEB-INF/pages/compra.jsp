<%-- 
    Document   : compra
    Created on : 01-mar-2015, 21:48:39
    Author     : heriberto
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList,java.util.List,mx.unam.posgrado.beans.*"%>
    <div class="row">
    <div class="col-md-10"> 
          <div class="row">
            <div class="col-md-6"><h5>Fecha:</h5></div>
            <div class="col-md-6"><h5>${compraContainer.fechaActual}</h5></div>
            <div class="col-md-6"><h5>Hora:</h5></div>
            <div class="col-md-6"><h5>${compraContainer.horaActual}</h5></div>
            <div class="col-md-6"><h5>Número de Pedido:</h5></div>
            <div class="col-md-6"><h5>67293AWT</h5></div>
            <div class="col-md-6"><h5>Número de guía de Pedido:</h5></div>
            <div class="col-md-6"><h5>FA45CX78</h5></div>
            <div class="col-md-6"><h5>Número de tarjeta de Crédito:</h5></div>
            <div class="col-md-6"><h5>${compraContainer.tarjetaCredito}</h5></div>
          </div> <!-- row -->
          <div class="row">
            <hr />
            <div class="col-md-12">
            <table class="table table-condensed">
            <thead>
            <tr>
            <th>Articulo</th>
            <th>Precio</th>
            <th> Cantidad </th>
            <th> Total </th>
            </tr>
            </thead> 
            <tbody>
             <c:forEach items="${purchaseContainer.purchaseArticuloList}" var="purchase" varStatus="status">

                       <tr>
                           <td>${purchase.descripcion}</td>
                           <td>$ ${purchase.precio}</td>
                           <td>${purchase.cantidad}</td>
                           <td>$ ${purchase.total}</td>
                       </tr>
              </c:forEach>
              <tr>
              <td></td>
              <td> <strong> SubTotal:</strong></td>
              <td colspan="2"> <strong> $ ${purchaseContainer.totalPurchase}</strong></td>
              </tr>
              <tr>
              <td></td>
              <td> <strong>Impuesto:</strong></td>
              <td colspan="2" > <strong> $ ${purchaseContainer.impuesto}</strong></td>
              </tr>
              <tr>
              <td></td>
              <td> <strong> Total: </strong></td>
              <td colspan="2" > <strong> $ ${purchaseContainer.totalPurchaseConImpuesto}
              </strong></td>
              </tr>      
            </tbody>
            </table>
            </div> <!-- fin col-md-12 dentro del row -->
        </div><!--row -->
        <div class="row">
          <hr />
          <div class="col-md-12">
            <table class="table table-condensed">
            <thead>
            <tr>
            <th>Empresa de envío</th>
            <th></th>
            <th></th>
            </tr>
            </thead>
            <tbody>
            <tr>
            <td>${compraContainer.empresaEnvio.descripcion}</td>
            <td> <strong>Importe:</strong></td>
            <td> <strong>$ ${compraContainer.empresaEnvio.tarifa}</strong></td>
            </tr>
            <tr>
            <td></td>
            <td> <strong>Total con Envío: </strong></td>
            <td> <strong>$ ${compraContainer.empresaEnvio.getTotal(purchaseContainer.totalPurchaseConImpuesto)}</strong></td>
            </tr>
            </tbody>
            </table>
          </div><!-- fin col-md-12-->
        </div> <!-- termina row -->
        <div class="row">
          <hr />
          <div class="text-center col-md-12">
          <strong>Puedes consultar en la siguiente página el estado de envío de tu compra:</strong>
          </div>
          <div class="text-center col-md-12"><strong><a href="#" class="text-primary" >http://wwww.dhl.com </a></strong></div>
          <div class="text-center col-md-12 ">
            <h3> <strong> <em>Gracias por tu compra!</em></strong> </h3>
          </div>
          <div class="text-center col-md-12 ">
              <hr />
                <a href="reiniciar.html" class="btn btn-warning" name="reniciar" >Regresar Inicio</a>
          </div>
        </div>
 

    </div> <!-- fin col-md-8 -->
    <div class="col-md-2"></div>
    </div>
        
 