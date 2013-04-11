package de.shop.bestellverwaltung.domain;

import java.security.Timestamp;
import java.util.Date;

import de.shop.kundenverwaltung.domain.AbstractKunde;

public class Bestellung {
	


		private Long Bestellungs_ID;
		private AbstractKunde Kunde_ID;
		private Date Bestelldatum;
		private boolean Status;
		private Long Version;
		private double Gesamtpreis;
		private Timestamp aktualisiert;
		
		//JsonIgnore
		
		public Long getBestellungs_ID() {
			return Bestellungs_ID;
		}
		public void setBestellungs_ID(Long bestellungs_ID) {
			Bestellungs_ID = bestellungs_ID;
		}
		public Date getBestelldatum() {
			return Bestelldatum;
		}
		public void setBestelldatum(Date bestelldatum) {
			Bestelldatum = bestelldatum;
		}
		public boolean isStatus() {
			return Status;
		}
		public void setStatus(boolean status) {
			Status = status;
		}
		public Long getVersion() {
			return Version;
		}
		public void setVersion(Long version) {
			Version = version;
		}
		public double getGesamtpreis() {
			return Gesamtpreis;
		}
		public void setGesamtpreis(double gesamtpreis) {
			Gesamtpreis = gesamtpreis;
		}
		public Timestamp getAktualisiert() {
			return aktualisiert;
		}
		public void setAktualisiert(Timestamp aktualisiert) {
			this.aktualisiert = aktualisiert;
		}
		public AbstractKunde getKunde_ID() {
			return Kunde_ID;
		}
		public void setKunde_ID(AbstractKunde kunde_ID) {
			Kunde_ID = kunde_ID;
		}
		
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime
					* result
					+ ((Bestellungs_ID == null) ? 0 : Bestellungs_ID.hashCode());
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
			if (Bestellungs_ID == null) {
				if (other.Bestellungs_ID != null)
					return false;
			} else if (!Bestellungs_ID.equals(other.Bestellungs_ID))
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "Bestellung [Bestellungs_ID=" + Bestellungs_ID
					+ ", Kunde_ID=" + Kunde_ID + ", Bestelldatum="
					+ Bestelldatum + ", Status=" + Status + ", Version="
					+ Version + ", Gesamtpreis=" + Gesamtpreis
					+ ", aktualisiert=" + aktualisiert + "]";
		}
			
}