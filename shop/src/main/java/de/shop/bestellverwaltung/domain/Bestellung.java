package de.shop.bestellverwaltung.domain;

import java.io.Serializable;
import java.net.URI;
import java.security.Timestamp;
import java.util.Date;

import de.shop.kundenverwaltung.domain.AbstractKunde;

public class Bestellung implements Serializable {
	private static final long serialVersionUID = 1L;
	
		private Long id;
		private StatusType status;
		private Long version;
		private double gesamtpreis;
		private Timestamp aktualisiert;
		
		private AbstractKunde kunde;
		private URI kundeUri;
		
		private Date bestelldatum;
		
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Date getBestelldatum() {
			return bestelldatum;
		}
		public void setBestelldatum(Date bestelldatum) {
			this.bestelldatum = bestelldatum;
		}
		public StatusType isStatus() {
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
		public double getGesamtpreis() {
			return gesamtpreis;
		}
		public void setGesamtpreis(double gesamtpreis) {
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
			Bestellung other = (Bestellung) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "Bestellung [id=" + id + ", status=" + status + ", version="
					+ version + ", gesamtpreis=" + gesamtpreis + ", kundeUri="
					+ kundeUri + ", bestelldatum=" + bestelldatum + "]";
		}


			
}