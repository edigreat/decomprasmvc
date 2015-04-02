package mx.unam.posgrado.validadores;

import mx.unam.posgrado.beans.ArticuloElegidoContainer;
import mx.unam.posgrado.beans.CompraContainer;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class CompraContainerValidator  implements Validator {

	private static final Logger logger = Logger.getLogger(CompraContainerValidator.class);

	@Override
	public boolean supports(Class<?> arg0) {
		return CompraContainer.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		logger.debug("Entrandoa a validar compraContainer");
		CompraContainer compraContainer = (CompraContainer)arg0;
		/*if(compraContainer.getTarjetaCredito()==null || compraContainer.getTarjetaCredito().trim().length()==0)
		{
			errors.rejectValue("tarjetaCredito", "default.blank.message");
		}*/
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tarjetaCredito", "default.null.message",new String []{"tarjeta de credito"});
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nipCredito", "default.null.message",new String []{"NIP"});
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codigoTransaccion", "default.null.message",new String []{"codigo de transaccion"});
		if(compraContainer.getEmpresaEnvio()==null ||compraContainer.getEmpresaEnvio().getIdentificador()==null){
			errors.rejectValue("empresaEnvio", "default.null.message",new Object[]{"Empresa De Envio"}, "");
		}
	}

}
