package it.prova.gestioneordinijpamaven.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ordine")
public class Ordine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "nomeDestinatario")
	private String noemDestinatario;
	@Column(name = "indirizzoSpedizione")
	private String indirizzoSpedizione;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ordine")
	private Set<Articolo> articoli = new HashSet<>();

	public Ordine() {
		// TODO Auto-generated constructor stub
	}

	public Ordine(String noemDestinatario, String indirizzoSpedizione) {
		super();
		this.noemDestinatario = noemDestinatario;
		this.indirizzoSpedizione = indirizzoSpedizione;
	}

	public Ordine(String noemDestinatario, String indirizzoSpedizione, Set<Articolo> articoli) {
		super();
		this.noemDestinatario = noemDestinatario;
		this.indirizzoSpedizione = indirizzoSpedizione;
		this.articoli = articoli;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNoemDestinatario() {
		return noemDestinatario;
	}

	public void setNoemDestinatario(String noemDestinatario) {
		this.noemDestinatario = noemDestinatario;
	}

	public String getIndirizzoSpedizione() {
		return indirizzoSpedizione;
	}

	public void setIndirizzoSpedizione(String indirizzoSpedizione) {
		this.indirizzoSpedizione = indirizzoSpedizione;
	}

	public Set<Articolo> getArticoli() {
		return articoli;
	}

	public void setArticoli(Set<Articolo> articoli) {
		this.articoli = articoli;
	}

	@Override
	public String toString() {
		return "Ordine [id=" + id + ", noemDestinatario=" + noemDestinatario + ", indirizzoSpedizione="
				+ indirizzoSpedizione + "]";
	}

}
