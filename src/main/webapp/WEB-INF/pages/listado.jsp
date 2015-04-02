<%-- 
    Document   : index.jsp
    Created on : 22-feb-2015, 22:22:11
    Author     : heriberto
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList,java.util.List,mx.unam.posgrado.beans.*"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	<form:form method="post" action="addarticulo.html" modelAttribute="articuloElegidoContainer">
		<div class="row">
			
			<table class="table table-striped table-condensed">
				<thead>
					<tr>
						<th></th> 
						<th><spring:message code="articulo.tabla.header.descripcion" /></th>
						<th> <spring:message code="articulo.tabla.header.precio" /> 
						</th>
						<th><spring:message code="articulo.tabla.header.cantidad" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${articuloList}" var="articulo" varStatus="status">
						<tr>
							<td><img src="${pageContext.request.contextPath}/static/img/${articulo.nombreImagen}" /></td>
							<td><c:out value="${articulo.descripcion}" /></td>
							<td><spring:message code="precio.tipomoneda.simbolo" /> <c:out value="${articulo.precio}" /> <spring:message code="precio.tipomoneda.descripcion" /></td>
							<td><input type="text" name="articulosElegidosList[${status.index}].cantidadForma" size="3" value="${articulo.cantidad}" />
							<div class="text-danger pull-left">
								<form:errors path="articulosElegidosList[${status.index}].cantidadForma" />
							</div>
							<input name="articulosElegidosList[${status.index}].identificador" type="hidden" value="${articulo.identificador}" />
							<input name="articulosElegidosList[${status.index}].descripcion" type="hidden" value="${articulo.descripcion}" />
							<input name="articulosElegidosList[${status.index}].precio" type="hidden" value="${articulo.precio}" />
							
							</td>
						</tr>

					</c:forEach>

				</tbody>
			</table>
		</div>
		<div class="col-md-4">
			<button type="submit" class="btn btn-primary" name="add">
				<spring:message code="articulo.boton.agregar" /> 
			</button>
		</div>
	</form:form>
	<div class="col-md-3">
		<a href="verificaCheckout.html"
			class="btn btn-success" name="checkout">
			<spring:message code="articulo.boton.checkout" /> 
			
		</a>
	</div>
	<div class="col-md-3">
		<a href="clear.html" class="btn btn-warning"
			name="clear">
			<spring:message code="articulo.boton.limpiar" /> 
 		</a>
	</div>
	
	<form:form method="post" action="updateCar.html" modelAttribute="articuloElegidoContainer">
		<div class="flotante">

			<button type="submit" class="btn btn-primary" name="update">
				<spring:message code="articulo.boton.actualizar" /> 
			</button>
		</div>
		<div class="row">
			<div class="col-md-12">
				<hr>
			</div>
		</div>
		<div class="row">
			<h2>
				<p class="bg-primary text-center">Tu carrito de compras</p>
			</h2>

		</div>

		<div class="row">
			<div class="col-md-12">
				<table class="table table-striped">
					<thead>
						<tr>
						<th><spring:message code="articulo.tabla.header.descripcion" /></th>
						<th><spring:message code="articulo.tabla.header.precio" /></th>
						<th><spring:message code="articulo.tabla.header.cantidad" /></th>
						<th><spring:message code="articulo.tabla.header.eliminar" /></th>
						<th><spring:message code="articulo.tabla.header.total" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${purchaseContainer.purchaseArticuloList}" var="purchase" varStatus="status">

							<tr>
								<td>${purchase.descripcion}</td>
								<td><spring:message code="precio.tipomoneda.simbolo" /> ${purchase.precio}</td>
								<td>${purchase.cantidad}</td>
								<td>
									<input type="checkbox" name="articulosElegidosList[${status.index}].habilitado"  />
									<input name="articulosElegidosList[${status.index}].identificador" type="hidden" value="${purchase.identificador}" />
									
								</td>
								<td><spring:message code="precio.tipomoneda.simbolo" /> ${purchase.total}</td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>
			<div class="col-md-offset-5 col-md-7">
				<h3>
					TOTAL COMPRA: <span class="label label-default">
					<spring:message code="precio.tipomoneda.simbolo" /> ${purchaseContainer.totalPurchase} <spring:message code="precio.tipomoneda.descripcion" /></span>
				</h3>
			</div>

		</div>
	</form:form>

