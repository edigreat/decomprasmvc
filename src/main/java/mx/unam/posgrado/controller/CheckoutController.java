package mx.unam.posgrado.controller;

import static mx.unam.posgrado.constantes.ConstantesShoppinCart.*;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;

import mx.unam.posgrado.beans.CompraContainer;
import mx.unam.posgrado.beans.EmpresaEnvio;
import mx.unam.posgrado.dao.ArticuloCompraDao;
import mx.unam.posgrado.validadores.CompraContainerValidator;
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

@Controller
@SessionAttributes({SESION_LISTA_EMPRESAS_ENVIO,SESION_CONTENEDOR_COMPRA})
public class CheckoutController implements Serializable {
	
	/**
	 * UID a utilizar
	 */
	private static final long serialVersionUID = 4642034253481229088L;
	/**
	 * Log a utilizar
	 */
	private static final Logger logger = Logger.getLogger(CheckoutController.class);
	/**
	 * Propiedad para almacenar las empresas de envio del catalogo.
	 */
	private List<EmpresaEnvio> empresaEnvioList;
    @Autowired
	private ArticuloCompraDao articuloCompraDao;

    /**
     * Coloca en sesion la lista de empresas de envio
     * @return empresas de envio
     */
	@ModelAttribute(SESION_LISTA_EMPRESAS_ENVIO)
    public List<EmpresaEnvio> getArticuloList() {
		logger.debug("Cargando lista de empresas " +this.empresaEnvioList);
		if(this.empresaEnvioList==null){
			this.empresaEnvioList=this.articuloCompraDao.obtenerEmpresaEnvio();
		}
        return this.empresaEnvioList;
    }
	
	@ModelAttribute(SESION_CONTENEDOR_COMPRA)
    public CompraContainer getCompraContainer() {
		logger.debug("Creando contenedor de compra ");
		return new CompraContainer();
    }
	
	/**
	 * Establece el validador para la entidad REQUEST_CONTENEDOR_COMPRA
	 * @param binder nombre de la entidad a validar
	 */
	 @InitBinder(SESION_CONTENEDOR_COMPRA)
	    public void initBinder(WebDataBinder binder) {
	        binder.setValidator(new CompraContainerValidator());
	}
	 
	
	@RequestMapping(value ="checkout.html",method = RequestMethod.GET )
	public ModelAndView showCheckout(@ModelAttribute(SESION_CONTENEDOR_COMPRA) CompraContainer compraContainer ) 
	{
		logger.debug("Entrando pagina principal de Checkout ");
		return new ModelAndView(VISTA_CHECKOUT_PRODUCTO,SESION_CONTENEDOR_COMPRA,compraContainer );
	}
	
	
	@RequestMapping(value ="confirmPurchase.html",method = { RequestMethod.GET, RequestMethod.POST } )
	public ModelAndView confirmPurchase(@Valid @ModelAttribute(SESION_CONTENEDOR_COMPRA)
	CompraContainer compraContainer,BindingResult resultado) 
	{
		if(resultado.hasErrors()){
			logger.debug("Errores en la confirmacion compra " + compraContainer );
			return new ModelAndView(VISTA_CHECKOUT_PRODUCTO,SESION_CONTENEDOR_COMPRA,compraContainer );
		}
		logger.debug("Confirmando compra " + compraContainer );
		int indice = empresaEnvioList.indexOf(compraContainer.getEmpresaEnvio());
		compraContainer.setEmpresaEnvio(empresaEnvioList.get(indice));
		logger.debug(compraContainer);
		return new ModelAndView(VISTA_COMPRA_PRODUCTO,SESION_CONTENEDOR_COMPRA,compraContainer );
	}
	
	@RequestMapping(value ="reiniciar.html",method = RequestMethod.GET )
	public String reiniciarCompra(SessionStatus status){
		status.setComplete();
		return "redirect:reiniciarInicio.html";
	}
}
