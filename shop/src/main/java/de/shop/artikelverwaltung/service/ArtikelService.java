package de.shop.artikelverwaltung.service;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.jboss.logging.Logger;

import com.google.common.base.Strings;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.artikelverwaltung.domain.KategorieType;
import de.shop.util.IdGroup;
import de.shop.util.Log;
import de.shop.util.ValidatorProvider;

@Log
public class ArtikelService implements Serializable {
	private static final long serialVersionUID = -5105686816948437276L;
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
	
	public List<Artikel> findVerfuegbareArtikel() {
		final List<Artikel> result = em.createNamedQuery(Artikel.FIND_VERFUEGBARE_ARTIKEL, Artikel.class)
				                       .getResultList();
		return result;
	}
	
	public Artikel findArtikelById(Long id, Locale locale) {
		validateArtikelId(id, locale);
		final Artikel artikel =  em.find(Artikel.class, id);
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

	//public List<Artikel> findAllArtikel() {
	//			final List<Artikel> allArtikel = em.createNamedQuery(Artikel.class);
	//			return allArtikel;
	//}

	
	public List<Artikel> findArtikelByBezeichnung(String bezeichnung) {
		if (Strings.isNullOrEmpty(bezeichnung)) {
			final List<Artikel> artikel = findVerfuegbareArtikel();
			return artikel;
		}
		
		final List<Artikel> artikel = em.createNamedQuery(Artikel.FIND_ARTIKEL_BY_BEZ, Artikel.class)
				                        .setParameter(Artikel.PARAM_BEZEICHNUNG, "%" + bezeichnung + "%")
				                        .getResultList();
		return artikel;
	}
	public List<Artikel> findArtikelByKategorie(KategorieType kategorie, Locale locale) {
		validateKategorie(kategorie, locale);
		if (kategorie == null) {
			final List<Artikel> artikel = findVerfuegbareArtikel();
			return artikel;
		}
		
		final List<Artikel> artikel = em.createNamedQuery(Artikel.FIND_ARTIKEL_BY_KAT, Artikel.class)
				                        .setParameter(Artikel.PARAM_KATEGORIE, "%" + kategorie + "%")
				                        .getResultList();
		return artikel;
	}
	
	
	public List<Artikel> findArtikelByFarbe(String farbe) {
		if (Strings.isNullOrEmpty(farbe)) {
			final List<Artikel> artikel = findVerfuegbareArtikel();
			return artikel;
		}
		
		final List<Artikel> artikel = em.createNamedQuery(Artikel.FIND_ARTIKEL_BY_FAR, Artikel.class)
				                        .setParameter(Artikel.PARAM_FARBE, "%" + farbe + "%")
				                        .getResultList();
		return artikel;
	}

	public Artikel createArtikel(Artikel artikel, Locale locale) {
		if (artikel == null) {
			return artikel;
		}

		// Werden alle Constraints beim Einfuegen gewahrt?
		validateArtikel(artikel, locale, Default.class);
		
		em.persist(artikel);
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

			em.merge(artikel);
			
			return artikel;
		}
	
}
