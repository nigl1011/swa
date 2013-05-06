package de.shop.artikelverwaltung.service;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.jboss.logging.Logger;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.artikelverwaltung.domain.KategorieType;
import de.shop.artikelverwaltung.service.InvalidArtikelIdException;
import de.shop.util.IdGroup;
import de.shop.util.Log;
import de.shop.util.Mock;
import de.shop.util.ValidatorProvider;

@Log
public class ArtikelService implements Serializable {
	private static final long serialVersionUID = -5105686816948437276L;
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
	
	public Artikel findArtikelById(Long id, Locale locale) {
		validateArtikelId(id, locale);
		// TODO id pruefen
		// TODO Datenbanzugriffsschicht statt Mock
		final Artikel artikel =  Mock.findArtikelById(id);
		return artikel;
	}
	private void validateArtikelId(Long artikelId, Locale locale) {
		final Validator validator = validatorProvider.getValidator(locale);
		final Set<ConstraintViolation<Artikel>> violations = validator.validateValue(Artikel.class,
				                                                                           "id",
				                                                                           artikelId,
				                                                                           IdGroup.class);
		if (!violations.isEmpty())
			throw new InvalidArtikelIdException(artikelId, violations);
	}

	public List<Artikel> findAllArtikel() {
		// TODO Datenbanzugriffsschicht statt Mock
				final List<Artikel> allArtikel = Mock.findAllArtikel();
				return allArtikel;
	}

	public List<Artikel> findArtikelByKategorie(KategorieType kategorie, Locale locale) {
	validateKategorie(kategorie, locale);
		
		// TODO Datenbanzugriffsschicht statt Mock
		final List<Artikel> allArtikelMitKategorie = Mock.findArtikelByKategorie(kategorie);
		return allArtikelMitKategorie;
	}
	
	private void validateKategorie(KategorieType kategorie, Locale locale) {
		/*final Validator validator = validatorProvider.getValidator(locale);
		final Set<ConstraintViolation<Artikel>> violations = validator.validateValue(Artikel.class,
				                                                                           "kategorie",
				                                                                           kategorie,
				                                                                           Default.class); */
	}

	public Artikel createArtikel(Artikel artikel,Locale locale) {
		if (artikel == null) {
			return artikel;
		}

		// Werden alle Constraints beim Einfuegen gewahrt?
		validateArtikel(artikel, locale, Default.class);

		// TODO Datenbanzugriffsschicht statt Mock
		artikel = Mock.createArtikel(artikel);

		return artikel;
	}
	private void validateArtikel(Artikel artikel, Locale locale, Class<?>... groups) {
		// Werden alle Constraints beim Einfuegen gewahrt?
		final Validator validator = validatorProvider.getValidator(locale);
		
		final Set<ConstraintViolation<Artikel>> violations = validator.validate(artikel, groups);
		if (!violations.isEmpty()) {
			throw new InvalidArtikelException(artikel, violations);
		}
	}

	public Artikel updateArtikel(Artikel artikel, Locale locale) {
			if (artikel == null) {
				return null;
			}

			// Werden alle Constraints beim Modifizieren gewahrt?
			validateArtikel(artikel, locale, Default.class, IdGroup.class);


			// TODO Datenbanzugriffsschicht statt Mock
			Mock.updateArtikel(artikel);
			
			return artikel;
		}
}