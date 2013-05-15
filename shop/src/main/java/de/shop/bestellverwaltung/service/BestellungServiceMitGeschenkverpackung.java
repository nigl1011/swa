package de.shop.bestellverwaltung.service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Locale;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import de.shop.bestellverwaltung.domain.Bestellung;


@Decorator
public abstract class BestellungServiceMitGeschenkverpackung implements BestellungService {
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	@Inject
	@Delegate
	@Any
	private BestellungService bs;

	@Override
	public Bestellung findBestellungById(Long id, Locale locale) {
		return bs.findBestellungById(id, locale);
	}

	@Override
	public List<Bestellung> findBestellungenByKundeId(Long kundeId, Locale locale) {
		return bs.findBestellungenByKundeId(kundeId, locale);
	}

	@Override
	public Bestellung createBestellung(Bestellung bestellung, Locale locale) {
		LOGGER.warn("Geschenkverpackung noch nicht implementiert");
		
		return bs.createBestellung(bestellung, locale);
	}
}
