package de.shop.bestellverwaltung.domain;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.net.URI;
import java.security.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import de.shop.kundenverwaltung.domain.AbstractKunde;
import de.shop.util.IdGroup;


public class Bestellung implements Serializable {
	private static final long serialVersionUID = 1L;


	private static final long MIN_ID = 1;
	private static final long MIN_VERSION = 1;

	
		@Min(value = MIN_ID, message = "{bestellverwaltung.bestellung.id.min}")
		private Long id;
		
		@Enumerated
		@NotNull(message = "{bestellverwaltung.bestellung.status.notEmpty}")
		private StatusType status;
		
		@Min(value = MIN_VERSION, message = "{bestellverwaltung.bestellung.version.min}", groups = IdGroup.class)
		private Long version;
		
		@NotNull(message = "{bestellverwaltung.bestellung.gesamtpreis.notEmpty}")
		private Double gesamtpreis;
		
		@Temporal(TIMESTAMP)
		private Timestamp aktualisiert;
		
		@NotNull(message = "{bestellverwaltung.bestellung.kunde.notNull}")
		@JsonIgnore
		private AbstractKunde kunde;
		
		@NotNull(message = "{bestellverwaltung.bestellung.bestellposten.notNull}")
		private List<Bestellposten> bestellposten;
		
		
		private URI kundeUri;
		private Date bestelldatum;
		
		
		
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
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
		public Long getVersion() {
			return version;
		}
		public void setVersion(Long version) {
			this.version = version;
		}
		public Double getGesamtpreis() {
			return gesamtpreis;
		}
		public void setGesamtpreis(Double gesamtpreis) {
			this.gesamtpreis = gesamtpreis;
		}
		public Timestamp getAktualisiert() {
			return aktualisiert;
		}
		public void setAktualisiert(Timestamp aktualisiert) {
			this.aktualisiert = aktualisiert;
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

		
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime
					* result
					+ ((id == null) ? 0 : id.hashCode());
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
			return "Bestellung [id=" + id + ", status=" + status + ", version="
					+ version + ", gesamtpreis=" + gesamtpreis 
					+ ", bestelldatum=" + bestelldatum + "]";


		}
		public List<Bestellposten> getBestellposten() {
			return bestellposten;
		}
		public void setBestellposten(List<Bestellposten> bestellposten) {
			this.bestellposten = bestellposten;
		}
		
		
}
