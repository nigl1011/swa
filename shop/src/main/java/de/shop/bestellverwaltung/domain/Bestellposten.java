package de.shop.bestellverwaltung.domain;

import static de.shop.util.Constants.KEINE_ID;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.net.URI;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.persistence.OrderColumn;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jboss.logging.Logger;

import de.shop.artikelverwaltung.domain.Artikel;



@Entity
@Table(name = "bestellposten")
@NamedQueries({
    @NamedQuery(name  = Bestellposten.FIND_LADENHUETER,
   	            query = "SELECT a"
   	            	    + " FROM   Artikel a"
   	            	    + " WHERE  a NOT IN (SELECT bp.artikel FROM Bestellposten bp)")
})

public class Bestellposten implements Serializable {
	
	
	private static final long serialVersionUID = -1427065293027144676L;
	private static final int ANZAHL_MIN = 1;
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	private static final String PREFIX = "Bestellposten.";
	public static final String FIND_LADENHUETER = PREFIX + "findLadenhueter";
	
	
	@Id
	@GeneratedValue
	@Column(nullable = false, updatable = false)
	private Long id = KEINE_ID;
	
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "artikel_fk", nullable = false)
	@NotNull(message = "{bestellverwaltung.bestellposition.artikel.notNull}")
	@JsonIgnore
	private Artikel artikel;
	
	/**@Column(nullable = false,updatable = true)
	private Long version;
	**/
	
	/**@Column(nullable = true, precision = 5, scale = 4)
	@NotNull(message = "{bestellverwaltung.bestellung.zwischenpreis.notNull}")
	private BigDecimal zwischenpreis;**/
	
	@Column(name = "anzahl", nullable = false)
	@Min(value = ANZAHL_MIN, message = "{bestellverwaltung.bestellposition.anzahl.min}")
	private short anzahl;
	
	@Transient
	private URI bestellungUri;
	@Transient
	private URI artikelUri;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "bestellung_fk", insertable = false, nullable = false, updatable = false)
	@OrderColumn(name = "idx", nullable = false)
	@NotNull(message = "{bestellverwaltung.bestellungposten.bestellung.notNull}")
	@JsonIgnore
	private Bestellung bestellung;
	
	@PostPersist
	private void postPersist() {
		LOGGER.debugf("Neue Bestellposition mit ID=%d", id);
	}
	
	public Bestellposten() {
		super();
	}
	
	public Bestellposten(Artikel artikel) {
		super();
		this.artikel = artikel;
		this.anzahl = 1;
	}
	
	public Bestellposten(Artikel artikel, short anzahl) {
		super();
		this.artikel = artikel;
		this.anzahl = anzahl;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	**/

	public Artikel getArtikel() {
		return artikel;
	}
	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}
	public URI getBestellungUri() {
		return bestellungUri;
	}
	public void setBestellungUri(URI bestellungUri) {
		this.bestellungUri = bestellungUri;
	}
	public URI getArtikelUri() {
		return artikelUri;
	}
	public void setArtikelUri(URI artikelUri) {
		this.artikelUri = artikelUri;
	}	
	public short getAnzahl() {
		return anzahl;
	}
	public void setAnzahl(short anzahl) {
		this.anzahl = anzahl;
	}
	public Bestellung getBestellung() {
		return bestellung;
	}

	public void setBestellung(Bestellung bestellung) {
		this.bestellung = bestellung;
	}


	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + anzahl;
		result = prime * result + ((artikel == null) ? 0 : artikel.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		//result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		final Bestellposten other = (Bestellposten) obj;
		
		// Bei persistenten Bestellpositionen koennen zu verschiedenen Bestellungen gehoeren
		// und deshalb den gleichen Artikel (s.u.) referenzieren, deshalb wird Id hier beruecksichtigt
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else if (!id.equals(other.id)) {
			return false;
		}

		// Wenn eine neue Bestellung angelegt wird, dann wird jeder zu bestellende Artikel
		// genau 1x referenziert (nicht zu verwechseln mit der "anzahl")
		if (artikel == null) {
			if (other.artikel != null) {
				return false;
			}
		}
		else if (!artikel.equals(other.artikel)) {
			return false;
		}
		
		return true;
	}


	
	@Override
	public String toString() {
		return "Bestellposten [id=" + id 
			    + ", artikel=" + artikel
				+  "]";
	}



}

