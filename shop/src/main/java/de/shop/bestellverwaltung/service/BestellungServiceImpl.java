package de.shop.bestellverwaltung.service;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.jboss.logging.Logger;

import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.kundenverwaltung.domain.AbstractKunde;
import de.shop.util.IdGroup;
import de.shop.util.Log;
import de.shop.util.Mock;
import de.shop.util.ValidatorProvider;

@Log
public class BestellungServiceImpl implements BestellungService, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -519454062519816252L;

	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	@Inject
	@NeueBestellung
	private transient Event<Bestellung> event;
	
	@Inject
	private ValidatorProvider validatorProvider;
	
	@PostConstruct
	private void postConstruct() {
		LOGGER.debugf("CDI-faehiges Bean %s wurde erzeugt", this);
	}
	
	@PreDestroy
	private void preDestroy() {
		LOGGER.debugf("CDI-faehiges Bean %s wird geloescht", this);
	}
	
	@Override
	public Bestellung findBestellungById(Long id, Locale locale) {
		validateBestellungId(id, locale);
		// TODO Datenbanzugriffsschicht statt Mock
		final Bestellung bestellung =  Mock.findBestellungById(id);
		return bestellung;
	}
	private void validateBestellungId(Long bestellungId, Locale locale) {
		final Validator validator = validatorProvider.getValidator(locale);
		final Set<ConstraintViolation<Bestellung>> violations = validator.validateValue(Bestellung.class,
				                                                                           "id",
				                                                                           bestellungId,
				                                                                           IdGroup.class);
		if (!violations.isEmpty())
			throw new InvalidBestellungIdException(bestellungId, violations);
	}


	@Override
	public List<Bestellung> findBestellungenByKundeId(Long kundeId, Locale locale) {
		// TODO Datenbanzugriffsschicht statt Mock
		validateKundenId(kundeId, locale);
		final List<Bestellung> bestellungByKundenId =  Mock.findBestellungenByKundeId(kundeId);
		return bestellungByKundenId;
	}
	private void validateKundenId(Long kundenId, Locale locale) {
	}
	
	@Override
	public Bestellung createBestellung(Bestellung bestellung, Locale locale) {
		//if(bestellung == null){
		//	return bestellung;
		//	}
		validateBestellung(bestellung, locale, Default.class);
		
		// TODO Datenbanzugriffsschicht statt Mock
		bestellung = Mock.createBestellung(bestellung);
		event.fire(bestellung);
		
		return bestellung;
	}
	
	private void validateBestellung(Bestellung bestellung, Locale locale, Class<?>... groups) {
		final Validator validator = validatorProvider.getValidator(locale);
		
		final Set<ConstraintViolation<Bestellung>> violations = validator.validate(bestellung);
		if (violations != null && !violations.isEmpty()) {
			LOGGER.debugf("createBestellung: violations=%s", violations);
			throw new InvalidBestellungException(bestellung, violations);
		}
	}
	@Override
	public Bestellung updateBestellung(Bestellung bestellung, AbstractKunde kunde, Locale locale) {
		if (bestellung == null) {
			return null;
		}

		// Werden alle Constraints beim Modifizieren gewahrt?
		validateBestellung(bestellung, locale, Default.class, IdGroup.class);

	
		
		
		// TODO Datenbanzugriffsschicht statt Mock
		Mock.updateBestellung(bestellung);
		return bestellung;
		}
	}


