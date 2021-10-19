package it.prova.gestioneordinijpamaven.dao.ordine;

import java.util.List;

import it.prova.gestioneordinijpamaven.dao.IBaseDAO;
import it.prova.gestioneordinijpamaven.model.Categoria;
import it.prova.gestioneordinijpamaven.model.Ordine;

public interface OrdineDAO extends IBaseDAO<Ordine> {

	Ordine getEagerArticoli(Long id) throws Exception;

	List<Ordine> findAllByCategoria(Categoria categoriaInstance) throws Exception;

}
