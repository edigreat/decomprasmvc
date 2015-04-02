package mx.unam.posgrado.validadores;
import mx.unam.posgrado.beans.ArticuloElegidoContainer;
import mx.unam.posgrado.beans.Purchase;
import mx.unam.posgrado.controller.ShoppingCartController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ArticuloElegidoContainerValidator implements Validator {

	public ArticuloElegidoContainerValidator(){
		
	}
	private static final Logger logger = Logger.getLogger(ShoppingCartController.class);
	@Override
	public boolean supports(Class<?> arg0) {
		return ArticuloElegidoContainer.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.debug("Entrando al validador");
		ArticuloElegidoContainer articuloElegidoContainer=(ArticuloElegidoContainer)target;
		logger.debug(articuloElegidoContainer);
		int indice=0;
		for(Purchase purchase:articuloElegidoContainer.getArticulosElegidosList()){
			try{
				int cantidad = Integer.parseInt(purchase.getCantidadForma()); 
				purchase.setCantidad(cantidad);
			}catch(Exception ex){
				logger.warn("Error al procesar la cantidad " + purchase.getCantidadForma()+" ->" +" articulosElegidosList["+indice+"].cantidadForma");
				errors.rejectValue("articulosElegidosList["+indice+"].cantidadForma", "default.cantidad.invalida");
			}
			indice+=1;
		}
		
	}

}
