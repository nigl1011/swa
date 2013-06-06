package de.shop.lieferverwaltung.service;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.jboss.logging.Logger;

import de.shop.lieferverwaltung.domain.Lieferung;
//import de.shop.lieferverwaltung.service.InvalidLieferIdException;
import de.shop.util.IdGroup;
import de.shop.util.Log;
import de.shop.util.ValidatorProvider;

@Log
public class LieferService implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	
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
	
	public Lieferung findLieferungById(Long id, Locale locale) {
		validateLieferungId(id, locale);
		// TODO id pruefen
		// TODO Datenbanzugriffsschicht statt Mock
//		return Mock.findLieferungById(id);
		// FIXME Mock entfernen
		return null;
	}
	private void validateLieferungId(Long lieferId, Locale locale) {
		final Validator validator = validatorProvider.getValidator(locale);
		final Set<ConstraintViolation<Lieferung>> violations = validator.validateValue(Lieferung.class,
				                                                                           "id",
				                                                                           lieferId,
				                                                                           IdGroup.class);
		if (!violations.isEmpty())
			throw new InvalidLieferIdException(lieferId, violations);
	}
	
	public Lieferung createLieferung(Lieferung lieferung, Locale locale) {
		if (lieferung == null) {
			return lieferung;
		}

		// Werden alle Constraints beim Einfuegen gewahrt?
		validateLieferung(lieferung, locale, Default.class);

//		lieferung = Mock.createLieferung(lieferung);
//
//		return lieferung;
		
		// FIXME Mock entfernen
		return null;
	}

	private void validateLieferung(Lieferung lieferung, Locale locale, Class<?>... groups) {
		// Werden alle Constraints beim Einfuegen gewahrt?
		final Validator validator = validatorProvider.getValidator(locale);
		
		final Set<ConstraintViolation<Lieferung>> violations = validator.validate(lieferung, groups);
		if (!violations.isEmpty()) {
			throw new InvalidLieferException(lieferung, violations);
		}
	}

	public Lieferung updateLieferung(Lieferung lieferung, Locale locale) {
		if (lieferung == null) {
			return null;
		}

		// Werden alle Constraints beim Modifizieren gewahrt?
		validateLieferung(lieferung, locale, Default.class, IdGroup.class);

		// TODO Datenbanzugriffsschicht statt Mock
//		Mock.updateLieferung(lieferung);
//		
//		return lieferung;
		
		// FIXME Mock entfernen
		return null;
	}

}
