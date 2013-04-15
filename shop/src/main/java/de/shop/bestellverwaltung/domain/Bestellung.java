package de.shop.bestellverwaltung.domain;

import java.security.Timestamp;
import java.util.Date;
import org.codehaus.jackson.annotate.JsonIgnore;
import de.shop.kundenverwaltung.domain.AbstractKunde;

public class Bestellung {
		private Long id;
		private StatusType status;
		private Long version;
		private double gesamtpreis;
		private Timestamp aktualisiert;
		@JsonIgnore
		private AbstractKunde kundeid;
		private Date bestelldatum;
		
		
		public Long getBestellungs_ID() {
			return id;
		}
		public void setBestellungs_ID(Long bestellungs_ID) {
			id = bestellungs_ID;
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
		public AbstractKunde getKunde_ID() {
			return kundeid;
		}
		public void setKunde_ID(AbstractKunde kunde_ID) {
			this.kundeid = kundeid;
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
			return "Bestellung [id=" + id + ", status=" + status 
					+ ", version=" + version + ", gesamtpreis=" + gesamtpreis 
					+ ", bestelldatum=" + bestelldatum + "]";
		}
			
}