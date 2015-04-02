<%-- 
    Document   : checkout
    Created on : 01-mar-2015, 17:52:40
    Author     : heriberto
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList,java.util.List,mx.unam.posgrado.beans.*"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="panel panel-primary">
	<div class="panel-heading">
   	 	<h3 class="panel-title"><spring:message code="checkout.panel.header" /></h3>
   	 </div>
   <div class="panel-body">
   
  <form:form method="post" action="confirmPurchase.html" modelAttribute="compraContainer" cssClass="form-horizontal" role="form">
  <div id="my-div" style="display:none;">
   <form:errors path="tarjetaCredito">
  	<c:set var="errorClassTarjeta" value="has-error has-feedback"/>
   </form:errors>
   
   <form:errors path="nipCredito" >
     	<c:set var="errorClassNip" value="has-error has-feedback"/>
   </form:errors>
   
   <form:errors path="codigoTransaccion">
     	<c:set var="errorClassTransaccion" value="has-error has-feedback"/>
   </form:errors>
 <form:errors path="empresaEnvio">
     	<c:set var="errorClassEmpresaEnvio" value="alert alert-danger"/>
   </form:errors>
	</div>
            <div class="row">
                <div class="col-md-12">
                <table class="table table-striped table-condensed">
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
                               <td></td>
                               <td><span class="text-muted" > <h5>SubTotal:</h5> </span></td>
                               <td> <span class="text-muted" > <h5>$ ${purchaseContainer.totalPurchase}</h5> </span></td>
                             </tr>
                             <tr>
                               <td></td>
                               <td></td>
                               <td><span class="text-muted" > <h5>Impuesto:</h5> </span></td>
                               <td> <span class="text-muted" > <h5>$ ${purchaseContainer.impuesto}</h5> </span></td>

                             </tr>
                             <tr>
                               <td></td>
                               <td></td>
                               <td> <div class="text-info" ><h5><strong> Total: </strong></h5> </div></td>
                               <td> <span class="text-info" > <h4><strong>
                                               $ ${purchaseContainer.totalPurchaseConImpuesto}
                                           </strong></h4> </span></td>

                        </tr>      
                    </tbody>
                    </table>
                    <hr />

                </div>

                <div class="col-md-12 ">
                    <div class="${errorClassEmpresaEnvio}">
	                	 <form:errors path="empresaEnvio" />	                	
	                </div>
						
                <table class="table table-striped table-condensed">
                   <thead>
                       <tr>
                           <th>Empresa de Envío</th>
                           <th>Importe</th>
                           <th>Seleccionar </th>
                           <th>Total</th>
                       </tr>
                   </thead>
                   <tbody>
                     
                        <c:forEach items="${empresaEnvioList}" var="empresa" varStatus="status">
                        
                       <tr>
                           <td>${empresa.descripcion}</td>
                           <td>$ ${empresa.getImporte(purchaseContainer.totalPurchaseConImpuesto)} </td>
                           <td>
                           	
                           		<form:radiobutton path="empresaEnvio.identificador" value="${empresa.identificador}" />
                          	</td>
                           <td>$ ${empresa.getTotal(purchaseContainer.totalPurchaseConImpuesto)} </td>
                       </tr>
                       </c:forEach>
                   </tbody>
                </table>
                </div>
                
            </div><!-- termina row -->

            <div class="row">
              <p></p>
              <hr />
              <div class="panel panel-default">
                  <div class="panel-heading">
                    <h3 class="panel-title">Introduce tu información</h3>
                  </div>

                  <div class="panel-body">
               <div class="col-md-10">
                   
                  <div class="form-group ${errorClassTarjeta}">
                 <form:label path="tarjetaCredito" class="control-label col-sm-6" >Número de Tarjeta de Crédito : </form:label>
                  <div class="col-sm-6">
                   <form:input path="tarjetaCredito" class="form-control" />
                   	<div class="text-danger pull-left">
						<form:errors path="tarjetaCredito" />
				  </div>
                  </div>
                  
                  </div>
                  <div class="form-group  ${errorClassNip}">
                  <form:label path="nipCredito" class="control-label col-sm-6">NIP de Tarjeta de Crédito :</form:label>
                  <div class="col-sm-6">
                  <form:input path="nipCredito" class="form-control"   />
                  <div class="text-danger pull-left">
						<form:errors path="nipCredito" />
				  </div>
                  </div>
                  </div>
                  <div class="form-group ${errorClassTransaccion}">
                      
                  <form:label path="codigoTransaccion" class="control-label col-sm-6">Código de Transacción :</form:label>
                  <div class="col-sm-6">
                    <form:input path="codigoTransaccion" class="form-control" />
                    <div class="text-danger pull-left">
						<form:errors path="codigoTransaccion" />
				  </div>
                  </div> 
                  </div>
               </div>
                      <div class="col-md-12">
                          <div class="col-md-4"> </div>
                          <div class="col-sm-4">
                   <button type="submit" class="btn btn-info">Enviar</button>
                   <a href="reiniciar.html" class="btn btn-warning" name="cancelar" >Cancelar</a> 
                   </div> 
                          <div class="col-md-4"> </div>
                          
                      </div>
             </div>
           </div>
           </div>
              </form:form>
  </div> <!-- final de panel body -->
</div><!-- panel panel-primary -->     
