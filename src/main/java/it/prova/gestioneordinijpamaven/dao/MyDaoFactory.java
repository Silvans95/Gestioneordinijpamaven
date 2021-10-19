package it.prova.gestioneordinijpamaven.dao;

import it.prova.gestioneordinijpamaven.dao.articolo.ArticoloDAO;
import it.prova.gestioneordinijpamaven.dao.articolo.ArticoloDAOImpl;
import it.prova.gestioneordinijpamaven.dao.categoria.CategoriaDAO;
import it.prova.gestioneordinijpamaven.dao.categoria.CategoriaDAOImpl;
import it.prova.gestioneordinijpamaven.dao.ordine.OrdineDAO;
import it.prova.gestioneordinijpamaven.dao.ordine.OrdineDAOImpl;

public class MyDaoFactory {

	// rendiamo questo factory SINGLETON
	private static ArticoloDAO articoloDAOInstance = null;
	private static CategoriaDAO categoriaDAOInstance = null;
	private static OrdineDAO ordineDAOInstance = null;

	
	public static ArticoloDAO getArticoloDAOInstance() {
		if (articoloDAOInstance == null)
			articoloDAOInstance = new ArticoloDAOImpl();
		return articoloDAOInstance;
	}

	public static CategoriaDAO getCategoriaDAOInstance(){
		if(categoriaDAOInstance == null)
			categoriaDAOInstance= new CategoriaDAOImpl();
		return categoriaDAOInstance;
	}
	
	public static OrdineDAO getOrdineDAOInstance(){
		if(ordineDAOInstance == null)
			ordineDAOInstance= new OrdineDAOImpl();
		return ordineDAOInstance;
	}

}
