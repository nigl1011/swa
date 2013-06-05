/*package de.shop.bestellverwaltung.service;

import java.util.List;
import java.util.Locale;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import de.shop.bestellverwaltung.domain.Bestellung;

@Decorator
public abstract class BestellverwaltungMitGeschenkverpackung implements
		BestellungService {

	@Inject
	@Delegate
	@Any
	private BestellungService bestellverwaltung;

	//public AbstractBestellverwaltungMitGeschenkverpackung() {
		// TODO Auto-generated constructor stub
//	}

	@Override
	public Bestellung findBestellungById(Long id, Locale locale) {
		// TODO Auto-generated method stub
		return bestellverwaltung.findBestellungById(id, locale);
	}

	@Override
	public List<Bestellung> findBestellungenByKundeId(Long kundeId, Locale locale) {
		// TODO Auto-generated method stub
		return bestellverwaltung.findBestellungenByKundeId(kundeId, locale);
	}

	@Override
	public Bestellung createBestellung(Bestellung bestellung,
			 Locale locale) {
		// TODO Auto-generated method stub
		return bestellverwaltung.createBestellung(bestellung, locale);
	}

}*/
