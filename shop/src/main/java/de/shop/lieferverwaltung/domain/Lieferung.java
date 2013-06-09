package de.shop.lieferverwaltung.domain;

import static de.shop.util.Constants.KEINE_ID;
import static javax.persistence.TemporalType.TIMESTAMP;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.logging.Logger;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.util.IdGroup;
import de.shop.util.PreExistingGroup;


@Entity
@Table(name = "lieferung")
@NamedQueries({
	@NamedQuery(name  = Lieferung.FIND_LIEFERUNGEN_BY_LIEFERNR_FETCH_BESTELLUNGEN,
                query = "SELECT l"
                	    + " FROM Lieferung l LEFT JOIN FETCH l.bestellungen"
			            + " WHERE l.lieferNr LIKE :" + Lieferung.PARAM_LIEFERNR)
})

public class Lieferung implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	private static final long MIN_ID = 1;
	
	private static final int LIEFERNR_LENGTH = 12;
	
	private static final String PREFIX = "Lieferung.";
	public static final String FIND_LIEFERUNGEN_BY_LIEFERNR_FETCH_BESTELLUNGEN =
		                       PREFIX + "findLieferungenByLieferNrFetchBestellungen";
	public static final String PARAM_LIEFERNR = "lieferNr";
	
	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	@Min(value = MIN_ID, message = "{lieferverwaltung.lieferung.id.min}", groups = IdGroup.class)
	private Long id = KEINE_ID;
	
	@Column(length = LIEFERNR_LENGTH, unique = true)
	@NotNull(message = "{bestellverwaltung.lieferung.lieferNr.notNull}")
	private String lieferNr;
	
	@Column(name = "transport_art_fk")
	@Enumerated
	private TransportType transportArt;
	
	@Column(nullable = false)
	@Temporal(TIMESTAMP)
	@JsonIgnore
	private Date lieferdatum;
	
	@Column(nullable = false)
	@Temporal(TIMESTAMP)
	@JsonIgnore
	private Date aktualisiert;

	@ManyToMany(mappedBy = "lieferungen", cascade = { PERSIST, MERGE })
	@NotEmpty(message = "{bestellverwaltung.lieferung.bestellungen.notEmpty}", groups = PreExistingGroup.class)
	@Valid
	@JsonIgnore
	private Set<Bestellung> bestellungen;

	public Lieferung() {
		super();
	}
	
	public Lieferung(String lieferNr, TransportType transportArt) {
		super();
		this.lieferNr = lieferNr;
		this.transportArt = transportArt;
	}
	
	@Transient
	private List<URI> bestellungUri;

	/*
	 * public Lieferung(Long id, Date lieferdatum, Timestamp aktuell) { super();
	 * this.id = id; this.lieferdatum = lieferdatum; this.aktuell = aktuell; }
	 */
	@PrePersist
	private void prePersist() {
		lieferdatum = new Date();
		aktualisiert = new Date();
	}
	
	@PostPersist
	private void postPersist() {
		LOGGER.debugf("Neue Lieferung mit ID=%d", id);
	}
	
	@PreUpdate
	private void preUpdate() {
		aktualisiert = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Date getLieferdatum() {
		return lieferdatum;
	}

	public void setLieferdatum(Date lieferdatum) {
		this.lieferdatum = lieferdatum;
	}

	public Date getAktualisiert() {
		return aktualisiert;
	}

	public void setAktualisiert(Date aktualisiert) {
		this.aktualisiert = aktualisiert;
	}

	public String getLieferNr() {
		return lieferNr;
	}
	public void setLieferNr(String lieferNr) {
		this.lieferNr = lieferNr;
	}

	public TransportType getTransportArt() {
		return transportArt;
	}

	public void setTransportArt(TransportType transportArt) {
		this.transportArt = transportArt;
	}
	public Set<Bestellung> getBestellungen() {
		return bestellungen == null ? null : Collections.unmodifiableSet(bestellungen);
	}
	
	public void setBestellungen(Set<Bestellung> bestellungen) {
		if (this.bestellungen == null) {
			this.bestellungen = bestellungen;
			return;
		}
		
		// Wiederverwendung der vorhandenen Collection
		this.bestellungen.clear();
		if (bestellungen != null) {
			this.bestellungen.addAll(bestellungen);
		}
	}
	
	public void addBestellung(Bestellung bestellung) {
		if (bestellungen == null) {
			bestellungen = new HashSet<>();
		}
		bestellungen.add(bestellung);
	}
	
	public List<Bestellung> getBestellungenAsList() {
		return bestellungen == null ? null : new ArrayList<>(bestellungen);
	}
	
	public void setBestellungenAsList(List<Bestellung> bestellungen) {
		this.bestellungen = bestellungen == null ? null : new HashSet<>(bestellungen);
	}

	public List<URI> getBestellungUri() {
		return bestellungUri;
	}

	public void setBestellungUri(List<URI> bestellungUri) {
		this.bestellungUri = bestellungUri;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Lieferung other = (Lieferung) obj;
		
		if (lieferNr == null) {
			if (other.lieferNr != null) {
				return false;
			}
		}
		else if (!lieferNr.equals(other.lieferNr)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "Lieferung [id=" + id + ", lieferNr=" 
		       + ", lieferdatum=" + lieferdatum
		       + ", aktualisiert=" + aktualisiert + ']';
	}

}
