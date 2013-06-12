package de.shop.bestellverwaltung.domain;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import static de.shop.util.Constants.KEINE_ID;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.shop.kundenverwaltung.domain.AbstractKunde;
import de.shop.lieferverwaltung.domain.Lieferung;
import de.shop.util.IdGroup;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;


import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonProperty;
import org.jboss.logging.Logger;



@Entity
@Table(name = "bestellung")
@NamedQueries({
	@NamedQuery(name  = Bestellung.FIND_BESTELLUNGEN_BY_KUNDE,
                query = "SELECT b"
			            + " FROM   Bestellung b"
						+ " WHERE  b.kunde = :" + Bestellung.PARAM_KUNDE),
   	@NamedQuery(name  = Bestellung.FIND_BESTELLUNG_BY_ID_FETCH_LIEFERUNGEN,
			    query = "SELECT DISTINCT b"
                        + " FROM   Bestellung b LEFT JOIN FETCH b.lieferungen"
   			            + " WHERE  b.id = :" + Bestellung.PARAM_ID),
	@NamedQuery(name  = Bestellung.FIND_KUNDE_BY_ID,
 			    query = "SELECT b.kunde"
                        + " FROM   Bestellung b"
  			            + " WHERE  b.id = :" + Bestellung.PARAM_ID)
})

public class Bestellung implements Serializable {


	private static final long serialVersionUID = -1900888975491172450L;
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	private static final String PREFIX = "Bestellung.";
	public static final String FIND_BESTELLUNGEN_BY_KUNDE = PREFIX + "findBestellungenByKunde";
	public static final String FIND_BESTELLUNG_BY_ID_FETCH_LIEFERUNGEN =
		                       PREFIX + "findBestellungenByIdFetchLieferungen";
	public static final String FIND_KUNDE_BY_ID = PREFIX + "findBestellungKundeById";
	
	public static final String PARAM_KUNDE = "kunde";
	public static final String PARAM_ID = "id";
	

	private static final long MIN_ID = 1;
	//private static final long MIN_VERSION = 1;

		@Id
		@GeneratedValue
		@Column(nullable = false, updatable = false)
		@Min(value = MIN_ID, message = "{bestellverwaltung.bestellung.id.min}", groups = IdGroup.class)
		private Long id = KEINE_ID;
		
		@Column(nullable = false)
		@Enumerated
		@NotNull(message = "{bestellverwaltung.bestellung.status.notEmpty}")
		private StatusType status;
		
		/**@Column(nullable = false,updatable = true)
		@Min(value = MIN_VERSION, message = "{bestellverwaltung.bestellung.version.min}", groups = IdGroup.class)
		private Long version;
		**/
		
		@Column(precision = 5, scale = 4,nullable = false)
		@NotNull(message = "{bestellverwaltung.bestellung.gesamtpreis.notEmpty}")
		private Double gesamtpreis;
		
		
		@ManyToOne(optional = false)
		@JoinColumn(name = "kunde_fk", nullable = false, insertable = false, updatable = false)
		@NotNull(message = "{bestellverwaltung.bestellung.kunde.notNull}")
		@JsonIgnore
		private AbstractKunde kunde;
		
		@OneToMany(fetch = EAGER, cascade = { PERSIST, REMOVE })
		@JoinColumn(name = "bestellung_fk", nullable = false)
		@OrderColumn(name = "idx", nullable = false)
		@NotNull(message = "{bestellverwaltung.bestellung.bestellposten.notNull}")
		@Valid
		private List<Bestellposten> bestellposten;
		
		
		@Column(nullable = false)
		@Temporal(TIMESTAMP)
		@JsonIgnore
		private Date erzeugt;
		
		@Column(nullable = false)
		@Temporal(TIMESTAMP)
		@JsonIgnore
		private Date aktualisiert;
		
		@ManyToMany
		@JoinTable(name = "bestellung_lieferung",
				   joinColumns = @JoinColumn(name = "bestellung_fk"),
				                 inverseJoinColumns = @JoinColumn(name = "lieferung_fk"))
		@JsonIgnore
		private Set<Lieferung> lieferungen;
		
		@Transient
		private URI lieferungenUri;
		
		
		@Transient
		@XmlElement(name = "kunde", required = true) 
		@JsonProperty
		private URI kundeUri;
		private Date bestelldatum;
		
		public Bestellung() {
			super();
		}
		
		public Bestellung(List<Bestellposten> bestellpositionen) {
			super();
			this.bestellposten = bestellpositionen;
		}


		@PrePersist
		private void prePersist() {
			erzeugt = new Date();
			aktualisiert = new Date();
		}
		
		@PostPersist
		private void postPersist() {
			LOGGER.debugf("Neue Bestellung mit ID=%d", id);
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

		public List<Bestellposten> getBestellpositionen() {
			if (bestellposten == null) {
				return null;
			}
			
			return Collections.unmodifiableList(bestellposten);
		}
		
		public void setBestellpositionen(List<Bestellposten> bestellpositionen) {
			if (this.bestellposten == null) {
				this.bestellposten = bestellpositionen;
				return;
			}
			
			// Wiederverwendung der vorhandenen Collection
			this.bestellposten.clear();
			if (bestellpositionen != null) {
				this.bestellposten.addAll(bestellpositionen);
			}
		}
		
		public Bestellung addBestellposition(Bestellposten bestellposition) {
			if (bestellposten == null) {
				bestellposten = new ArrayList<>();
			}
			bestellposten.add(bestellposition);
			return this;
		}
		
		
	
		public Date getBestelldatum() {
			return (Date) bestelldatum.clone();
		}
		public void setBestelldatum(Date bestelldatum) {
			this.bestelldatum = (Date) bestelldatum.clone();
		}
		

		public StatusType getStatus() {
			return status;
		}
		public void setStatus(StatusType status) {
			this.status = status;
		}
		/**public Long getVersion() {
			return version;
		}
		public void setVersion(Long version) {
			this.version = version;
		}
		**/
		public Double getGesamtpreis() {
			return gesamtpreis;
		}
		public void setGesamtpreis(Double gesamtpreis) {
			this.gesamtpreis = gesamtpreis;
		
		}
		public AbstractKunde getKunde() {
			return kunde;
		}
		public void setKunde(AbstractKunde kunde) {
			this.kunde = kunde;
		}
		public URI getKundeUri() {
			return kundeUri;
		}
		public void setKundeUri(URI kundeUri) {
			this.kundeUri = kundeUri;
		}
		public Set<Lieferung> getLieferungen() {
			return lieferungen == null ? null : Collections.unmodifiableSet(lieferungen);
		}
		
		public void setLieferungen(Set<Lieferung> lieferungen) {
			if (this.lieferungen == null) {
				this.lieferungen = lieferungen;
				return;
			}
			
			// Wiederverwendung der vorhandenen Collection
			this.lieferungen.clear();
			if (lieferungen != null) {
				this.lieferungen.addAll(lieferungen);
			}
		}
		
		public void addLieferung(Lieferung lieferung) {
			if (lieferungen == null) {
				lieferungen = new HashSet<>();
			}
			lieferungen.add(lieferung);
		}
		
		@JsonIgnore
		public List<Lieferung> getLieferungenAsList() {
			return lieferungen == null ? null : new ArrayList<>(lieferungen);
		}

		public void setLieferungenAsList(List<Lieferung> lieferungen) {
			this.lieferungen = lieferungen == null ? null : new HashSet<>(lieferungen);
		}
		
		public URI getLieferungenUri() {
			return lieferungenUri;
		}
		public void setLieferungenUri(URI lieferungenUri) {
			this.lieferungenUri = lieferungenUri;
		}

		@JsonProperty("datum")
		public Date getErzeugt() {
			return erzeugt == null ? null : (Date) erzeugt.clone();
		}
		public void setErzeugt(Date erzeugt) {
			this.erzeugt = erzeugt == null ? null : (Date) erzeugt.clone();
		}
		public Date getAktualisiert() {
			return aktualisiert == null ? null : (Date) aktualisiert.clone();
		}
		public void setAktualisiert(Date aktualisiert) {
			this.aktualisiert = aktualisiert == null ? null : (Date) aktualisiert.clone();
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime
					* result
					+ ((id == null) ? 0 : id.hashCode());
			result = prime * result + ((kunde == null) ? 0 : kunde.hashCode());
			result = prime * result + ((erzeugt == null) ? 0 : erzeugt.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Bestellung other = (Bestellung) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			}
			else if (!id.equals(other.id))
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "Bestellung [id=" + id + ", status=" + status 
					+ ", gesamtpreis=" + gesamtpreis 
					+ ", erzeugt=" + erzeugt 
					+ ", bestelldatum=" + bestelldatum + "]";


		}
		public List<Bestellposten> getBestellposten() {
			return bestellposten;
		}
		public void setBestellposten(List<Bestellposten> bestellposten) {
			this.bestellposten = bestellposten;
		}
		
		
}
