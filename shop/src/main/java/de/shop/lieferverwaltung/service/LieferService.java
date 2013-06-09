package de.shop.lieferverwaltung.service;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.jboss.logging.Logger;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.artikelverwaltung.service.InvalidArtikelIdException;
import de.shop.lieferverwaltung.domain.Lieferung;
//import de.shop.lieferverwaltung.service.InvalidLieferIdException;
import de.shop.util.IdGroup;
import de.shop.util.Log;
import de.shop.util.ValidatorProvider;

@Log
public class LieferService implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	@PersistenceContext
	private transient EntityManager em;
	
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
		final Lieferung lieferung = em.find(Lieferung.class, id);
		return lieferung;
	}

	//public List<Lieferung> FindLieferungByBestellung ()

	private void validateLieferungId(Long Id, Locale locale) {
		final Validator validator = validatorProvider.getValidator(locale);
		final Set<ConstraintViolation<Artikel>> violations = validator
				.validateValue(Artikel.class, "id", Id, IdGroup.class);
		if (!violations.isEmpty())
			throw new InvalidArtikelIdException(Id, violations);
	}
	
	public Lieferung createLieferung(Lieferung lieferung, Locale locale) {
		if (lieferung == null) {
			return lieferung;
		}
		// Werden alle Constraints beim Einfuegen gewahrt?
		validateLieferung(lieferung, locale, Default.class);

		em.persist(lieferung);
		return lieferung;
	}

	private void validateLieferung(Lieferung lieferung, Locale locale,
			Class<?>... groups) {
		// Werden alle Constraints beim Einfuegen gewahrt?
		final Validator validator = validatorProvider.getValidator(locale);

		final Set<ConstraintViolation<Lieferung>> violations = validator
				.validate(lieferung, groups);
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
		em.merge(lieferung);

		return lieferung;
	}
}


