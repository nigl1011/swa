package de.shop.bestellverwaltung.service;

import java.util.List;
import java.util.Locale;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.kundenverwaltung.domain.AbstractKunde;

@Decorator
public abstract class BestellverwaltungMitGeschenkverpackung implements
		BestellungService {

	@Inject
	@Delegate
	@Any
	private BestellungService bestellverwaltung;

	public BestellverwaltungMitGeschenkverpackung() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Bestellung findBestellungById(Long id) {
		// TODO Auto-generated method stub
		return bestellverwaltung.findBestellungById(id);
	}

	@Override
	public List<Bestellung> findBestellungenByKundeId(Long kundeId) {
		// TODO Auto-generated method stub
		return bestellverwaltung.findBestellungenByKundeId(kundeId);
	}

	@Override
	public Bestellung createBestellung(Bestellung bestellung,
			AbstractKunde kunde, Locale locale) {
		// TODO Auto-generated method stub
		return bestellverwaltung.createBestellung(bestellung, kunde, locale);
	}

}
