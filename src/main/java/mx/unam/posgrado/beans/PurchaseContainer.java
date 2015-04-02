/**
 * 
 */
package mx.unam.posgrado.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import mx.unam.posgrado.constantes.ConstantesShoppinCart;

import org.apache.log4j.Logger;

/**
 * @author heriberto
 *
 */
public class PurchaseContainer implements Serializable {

	private static final Logger logger =  Logger.getLogger(PurchaseContainer.class);
	/**
	 * UID a utilizar
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Purchase> purchaseArticuloList;
	
	/**
	 * Constructor publico
	 */
	public PurchaseContainer(){
		purchaseArticuloList = new ArrayList<Purchase>();
	}
	
	/**
	 * Obtiene la suma de todos los articulos elegidos
	 * @return resultado de la suma
	 */
	public double getTotalPurchase(){
		double resultado=0.0;
		for(Purchase purchase:this.purchaseArticuloList){
			resultado += purchase.getTotal();
		}
		return resultado;
	}
	


	
	/***
	 * Calcula el impuesto de acuerdo al total
	 * @return
	 */
	public double getImpuesto(){
       double impuesto=0;
       double total =  getTotalPurchase();
       if ( total <= 3567.00 ){
           impuesto = ConstantesShoppinCart.IMPUESTO_01;
       } 
       if ( total > 3567.00 && total <= 5678.00 ){
            impuesto = ConstantesShoppinCart.IMPUESTO_02;

       }
        if ( total > 5678.00 ){
            impuesto = ConstantesShoppinCart.IMPUESTO_03;

        }
        return impuesto;
	}

	/**
	 * Calcula el total de compra con impuesto
	 * @return
	 */
	public double getTotalPurchaseConImpuesto(){
		double total = getTotalPurchase();
		double impuesto=getImpuesto();
		BigDecimal totalConImpuesto = new BigDecimal(total + (total*impuesto));
	    totalConImpuesto = totalConImpuesto.setScale(3, RoundingMode.HALF_UP);
		return totalConImpuesto.doubleValue();
	}
	/**
	 * Agrega un articulo a la lista de articulos
	 * @param articulo a agregar
	 */
	public void addPurchaseArticuloList(Purchase purchase){
		purchaseArticuloList.add(purchase);
	}

	/**
	 * Suma la cantidad a un articulo ya elegido
	 * @param purchase
	 */
	public void sumaCantidadArticulo(Purchase purchase){
		int indice = purchaseArticuloList.indexOf(purchase);
		Purchase purchaseOld = purchaseArticuloList.get(indice);
		purchaseOld.setCantidad(purchaseOld.getCantidad() + purchase.getCantidad());
	}

	/**
	 * Elimina un articulo de los elegidos
	 * @param purchase
	 */
	public void removePurchaseArticuloList(Purchase purchase){
		//int indice = purchaseArticuloList.indexOf(purchase);
		purchaseArticuloList.remove(purchase);
	}
	
	/**
	 * Reinicia la lista de articulos seleccionados
	 */
	public void reiniciarPurchaseArticuloMap(){
			purchaseArticuloList.clear();
	}
	
	public List<Purchase> getPurchaseArticuloList() {
		return purchaseArticuloList;
	}
	public void setPurchaseArticuloList(List<Purchase> purchaseArticuloList) {
		this.purchaseArticuloList = purchaseArticuloList;
	}

	@Override
	public String toString() {
		String resultado="";
		for(Purchase purchase:purchaseArticuloList){
			resultado+=purchase;
		}
		return resultado;
	}
	
	
	
	

}
