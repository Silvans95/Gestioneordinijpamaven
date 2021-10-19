package it.prova.gestioneordinijpamaven.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categoria")

public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "descrizione")
	private String descrizione;
	@Column(name = "categoria")
	private String categoria;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "categorie")
	private Set<Articolo> articoli = new HashSet<Articolo>();

	// ###################################################################################################

	public Categoria() {
		// TODO Auto-generated constructor stub
	}

	public Categoria(String descrizione, String categoria) {
		super();
		this.descrizione = descrizione;
		this.categoria = categoria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Set<Articolo> getArticoli() {
		return articoli;
	}

	public void setArticoli(Set<Articolo> articoli) {
		this.articoli = articoli;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", descrizione=" + descrizione + ", categoria=" + categoria + ", articoli="
				+ articoli + "]";
	}

}
