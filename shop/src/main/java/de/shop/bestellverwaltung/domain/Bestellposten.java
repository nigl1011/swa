package de.shop.bestellverwaltung.domain;


public class Bestellposten {

	private Long Positions_ID;
	private Long Menge;
	private Long Version;
	private Long Zwischenpreis;
	private Bestellung Bestellung_ID;
	
	
	
	public Long getPositions_ID() {
		return Positions_ID;
	}
	public void setPositions_ID(Long positions_ID) {
		Positions_ID = positions_ID;
	}
	public Long getMenge() {
		return Menge;
	}
	public void setMenge(Long menge) {
		Menge = menge;
	}
	public Long getVersion() {
		return Version;
	}
	public void setVersion(Long version) {
		Version = version;
	}
	public Long getZwischenpreis() {
		return Zwischenpreis;
	}
	public void setZwischenpreis(Long zwischenpreis) {
		Zwischenpreis = zwischenpreis;
	}
	public Bestellung getBestellung_ID() {
		return Bestellung_ID;
	}
	public void setBestellung_ID(Bestellung bestellung_ID) {
		Bestellung_ID = bestellung_ID;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((Positions_ID == null) ? 0 : Positions_ID.hashCode());
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
		Bestellposten other = (Bestellposten) obj;
		if (Positions_ID == null) {
			if (other.Positions_ID != null)
				return false;
		} else if (!Positions_ID.equals(other.Positions_ID))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Bestellposten [Positions_ID=" + Positions_ID + ", Menge="
				+ Menge + ", Version=" + Version + ", Zwischenpreis="
				+ Zwischenpreis + ", Bestellung_ID=" + Bestellung_ID + "]";
	}	
}

