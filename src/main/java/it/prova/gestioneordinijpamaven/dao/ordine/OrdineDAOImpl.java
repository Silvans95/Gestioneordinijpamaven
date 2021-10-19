package it.prova.gestioneordinijpamaven.dao.ordine;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordinijpamaven.model.Categoria;
import it.prova.gestioneordinijpamaven.model.Ordine;

public class OrdineDAOImpl implements OrdineDAO {

	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Ordine> list() throws Exception {

		return entityManager.createQuery("from Ordine", Ordine.class).getResultList();
	}

	@Override
	public Ordine get(Long id) throws Exception {
		return entityManager.find(Ordine.class, id);
	}

	@Override
	public void update(Ordine ordineInstance) throws Exception {
		if (ordineInstance == null) {
			throw new Exception("Problema valore in input");
		}
		ordineInstance = entityManager.merge(ordineInstance);
	}

	@Override
	public void insert(Ordine ordineInstance) throws Exception {
		if (ordineInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(ordineInstance);
	}
	
	@Override
	public void delete(Ordine ordineInstance) throws Exception {
		if (ordineInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(ordineInstance));
	}

	@Override
	public Ordine getEagerArticoli(Long id) throws Exception {
		TypedQuery<Ordine> query = entityManager.createQuery("from Ordine m left join fetch m.articoli where m.id = ?1",
				Ordine.class);
		query.setParameter(1, id);

		return query.getResultStream().findFirst().orElse(null);
	}
	
	//###################### METODI NON CRUDE #########################################
	
	@Override
	public List<Ordine> findAllByCategoria(Categoria categoriaInstance) throws Exception {
		TypedQuery<Ordine> query = entityManager.createQuery(
				"select o from Ordine o join o.articoli a join a.categorie c where c.descrizione like ?1", Ordine.class);
		return query.setParameter(1, categoriaInstance.getDescrizione()).getResultList();
	}

}
