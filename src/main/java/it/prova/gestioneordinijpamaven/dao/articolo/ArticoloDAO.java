package it.prova.gestioneordinijpamaven.dao.articolo;

import it.prova.gestioneordinijpamaven.dao.IBaseDAO;
import it.prova.gestioneordinijpamaven.model.Articolo;
import it.prova.gestioneordinijpamaven.model.Categoria;

public interface ArticoloDAO extends IBaseDAO<Articolo> {

	Articolo findByDescrizione(String descrizioneInput) throws Exception;

	Articolo findByIdFetchingCategorie(Long id) throws Exception;

	Long findSommaArticoloCategoria(Categoria categoriaInstance);

}
