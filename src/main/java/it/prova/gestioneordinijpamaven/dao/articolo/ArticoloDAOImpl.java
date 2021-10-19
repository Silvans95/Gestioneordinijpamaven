package it.prova.gestioneordinijpamaven.dao.articolo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordinijpamaven.model.Articolo;
import it.prova.gestioneordinijpamaven.model.Categoria;

public class ArticoloDAOImpl implements ArticoloDAO {

	private EntityManager entityManager;

	@Override
	public List<Articolo> list() throws Exception {
		return entityManager.createQuery("from Articolo", Articolo.class).getResultList();
	}

	@Override
	public Articolo get(Long id) throws Exception {
		return entityManager.find(Articolo.class, id);
	}

	@Override
	public void update(Articolo input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
	}

	@Override
	public void insert(Articolo input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
	}

	@Override
	public void delete(Articolo input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(input));
	}

	@Override
	public Articolo findByDescrizione(String descrizioneInput) throws Exception {
		TypedQuery<Articolo> query = entityManager
				.createQuery("select a from Articolo a where a.descrizione=?1", Articolo.class)
				.setParameter(1, descrizioneInput);

		return query.getResultStream().findFirst().orElse(null);
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Articolo findByIdFetchingCategorie(Long id) throws Exception {
		TypedQuery<Articolo> query = entityManager.createQuery(
				"select c FROM Articolo c left join fetch c.categorie g where c.id = :idArticolo", Articolo.class);
		query.setParameter("idArticolo", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public Long findSommaArticoloCategoria(Categoria categoriaInstance) {
		TypedQuery<Long> query = entityManager.createQuery(
				"select SUM(a.prezzoSingolo) from Articolo a join a.categorie c where c.id = ?1", Long.class);
		return query.setParameter(1, categoriaInstance.getId()).getSingleResult();
	}

	// ################################ METODI NON CRUDE ###############################################

}
