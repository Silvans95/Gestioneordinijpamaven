package it.prova.gestioneordinijpamaven.service.ordine;

import java.util.List;

import it.prova.gestioneordinijpamaven.dao.ordine.OrdineDAO;
import it.prova.gestioneordinijpamaven.model.Categoria;
import it.prova.gestioneordinijpamaven.model.Ordine;

public interface OrdineService {

	public List<Ordine> listAllOrdini() throws Exception;

	public Ordine caricaSingoloOrdine(Long id) throws Exception;

	public Ordine caricaSingoloOrdineConArticoli(Long id) throws Exception;

	public void aggiorna(Ordine ordineInstance) throws Exception;

	public void inserisciNuovo(Ordine ordineInstance) throws Exception;

	public void rimuovi(Ordine ordineInstance) throws Exception;

	// per injection
	public void setOrdineDAO(OrdineDAO ordineDAO);

	List<Ordine> caricaOrdiniEffettuatiPerCategoria(Categoria categoriaInstance) throws Exception;
}
