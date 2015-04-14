package mx.unam.posgrado.controller;
import mx.unam.posgrado.beans.Articulo;
import mx.unam.posgrado.beans.ArticuloElegidoContainer;
import mx.unam.posgrado.beans.Purchase;
import mx.unam.posgrado.beans.PurchaseContainer;
import mx.unam.posgrado.dao.ArticuloCompraDao;
import mx.unam.posgrado.validadores.ArticuloElegidoContainerValidator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import static mx.unam.posgrado.constantes.ConstantesShoppinCart.*;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;
@Controller
@SessionAttributes({SESION_LISTA_ARTICULOS,SESION_CONTENEDOR_PURCHASE})
public class ShoppingCartController implements Serializable {
	
	/**
	 * UID a utilizar
	 */
	private static final long serialVersionUID = -1816510732830285670L;
	/**
	 * Log a utilizar
	 */
	private static final Logger logger = Logger.getLogger(ShoppingCartController.class);
	
	@Autowired
	ArticuloCompraDao articuloCompraDao;
	
	
	/**
	 * Propiedad para almacenar la lista de articulos
	 * del catalogo
	 */
	private List<Articulo> articuloList =null;
	/**
	 * Propiedad para almacenar la lista de articulos
	 * elegidos antes de su confirmacion.
	 */
	//private PurchaseContainer purchaseContainer=null;
	
	/**
	 * Coloca en sesion la lista de articulos
	 * @return lista de articulos del catalogo
	 */
	@ModelAttribute(SESION_LISTA_ARTICULOS)
    public List<Articulo> getArticuloList() {
		logger.debug("Cargando lista de atributos " +this.articuloList);
		if(this.articuloList==null){
			this.articuloList=this.articuloCompraDao.obtenerListaArticulos();
		}
        return this.articuloList;
    }
	/**
	 * Coloca en sesion la lista de articulos
	 * elegidos antes de ser confirmados
	 * @return lista de articulos elegidos
	 */
	@ModelAttribute(SESION_CONTENEDOR_PURCHASE)
    public PurchaseContainer getPurchaseContainer() {
		logger.debug("Cargando PurchaseContainer ");
		/*if(purchaseContainer==null){
			this.purchaseContainer=new PurchaseContainer();
		}*/
        return new PurchaseContainer();
    }
	/**
	 * Establece el validador para la entidad REQUEST_CONTENEDOR_ARTICULOS_ELEGIDOS
	 * @param binder nombre de la entidad a validar
	 */
	 @InitBinder(REQUEST_CONTENEDOR_ARTICULOS_ELEGIDOS)
	    public void initBinder(WebDataBinder binder) {
	        binder.setValidator(new ArticuloElegidoContainerValidator());
	}

	@RequestMapping(value =PAGINA_HTML_DE_COMPRAS,method = RequestMethod.GET )
	public ModelAndView showCarroCompras() 
	{
		logger.debug("Entrando pagina principal ");
		ArticuloElegidoContainer articuloElegidoContainer = new ArticuloElegidoContainer();
		return new ModelAndView(VISTA_CATALOGO_PRODUCTO,REQUEST_CONTENEDOR_ARTICULOS_ELEGIDOS,articuloElegidoContainer );
	}
	
	
	@RequestMapping( value =REQUEST_ADD_PRODUCTO, method = RequestMethod.POST )
	public String addPurchaseArticulo(
			@ModelAttribute(SESION_CONTENEDOR_PURCHASE) 
			PurchaseContainer purchaseContainer,
			@Valid @ModelAttribute(REQUEST_CONTENEDOR_ARTICULOS_ELEGIDOS)
			ArticuloElegidoContainer articuloElegidoContainer,
			BindingResult resultado) 
	{
		

		if(resultado.hasErrors()){
			return VISTA_CATALOGO_PRODUCTO;
		}
		logger.debug("Entrando a agregar Articulo " + articuloElegidoContainer);
		for(Purchase purchase:articuloElegidoContainer.getArticulosElegidosList())
		{
			if(purchase.getCantidad()>0)
			{
				
				if(!purchaseContainer.getPurchaseArticuloList().contains(purchase))
				{
					Purchase purchaseAdded = new Purchase(purchase.getIdentificador(),purchase.getDescripcion(),
							purchase.getPrecio(),purchase.getCantidad(),purchase.getCantidadForma());
					logger.debug("Se agrega " + purchaseAdded);
					purchaseContainer.addPurchaseArticuloList(purchaseAdded);
				}
				else
				{
					purchaseContainer.sumaCantidadArticulo(purchase);
				}
			}
		}
		return INDICADOR_REDIRECCION + PAGINA_HTML_DE_COMPRAS;
	}


			
	@RequestMapping( value =PAGINA_HTML_VERIFICA_CHECKOUT, method = RequestMethod.GET )
	public String realizarCheckout(@ModelAttribute(SESION_CONTENEDOR_PURCHASE) PurchaseContainer purchaseContainer) {
		logger.debug("Entrando a checkout " +purchaseContainer.getPurchaseArticuloList().size());
		if(purchaseContainer.getPurchaseArticuloList().size()<=0){
			return INDICADOR_REDIRECCION + PAGINA_HTML_DE_COMPRAS;
		}
		return INDICADOR_FORWARD + PAGINA_HTML_CHECKOUT;
	}

	@RequestMapping( value =REQUEST_CLEAR_PRODUCTO, method = RequestMethod.GET )
	public String reiniciarCarro(@ModelAttribute(SESION_CONTENEDOR_PURCHASE) PurchaseContainer purchaseContainer) {
		logger.debug("Entrando a clear");
		purchaseContainer.getPurchaseArticuloList().clear();
		return INDICADOR_REDIRECCION + PAGINA_HTML_DE_COMPRAS;
	}
	
	@RequestMapping( value =REQUEST_UPDATE_PRODUCTO, method = RequestMethod.POST )
	public String actualizarCarro(
			@ModelAttribute(REQUEST_CONTENEDOR_ARTICULOS_ELEGIDOS)
			ArticuloElegidoContainer articuloElegidoContainer,
			@ModelAttribute(SESION_CONTENEDOR_PURCHASE) 
			PurchaseContainer purchaseContainer) {
		logger.debug("Entrando a update " + articuloElegidoContainer);
		
		Iterator<Purchase> purchaseIterator = articuloElegidoContainer.getArticulosElegidosList().iterator();
		while (purchaseIterator.hasNext()) {
			Purchase purchaseActual = purchaseIterator.next();
			if(purchaseActual.getHabilitado()){
				logger.debug("Articulo a remover " + purchaseActual);
				purchaseContainer.removePurchaseArticuloList(purchaseActual);

			}
		}
		return INDICADOR_REDIRECCION + PAGINA_HTML_DE_COMPRAS;
	}
	
	@RequestMapping(value =PAGINA_HTML_REINICIO_PRINCIPAL,method = RequestMethod.GET )
	public String reiniciarCompra( @ModelAttribute(SESION_CONTENEDOR_PURCHASE) PurchaseContainer purchaseContainer, SessionStatus status){
		logger.debug("Borrando a " + purchaseContainer);
		status.setComplete();
		return INDICADOR_REDIRECCION + PAGINA_HTML_DE_COMPRAS;
	}
}
