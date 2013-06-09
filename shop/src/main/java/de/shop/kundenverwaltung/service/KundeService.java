package de.shop.kundenverwaltung.service;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import javax.ws.rs.Consumes;


import org.jboss.logging.Logger;

import de.shop.bestellverwaltung.domain.Bestellposten;
import de.shop.bestellverwaltung.domain.Bestellposten_;
import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.bestellverwaltung.domain.Bestellung_;
import de.shop.kundenverwaltung.domain.AbstractKunde;
import de.shop.kundenverwaltung.domain.AbstractKunde_;
import de.shop.kundenverwaltung.domain.PasswordGroup;
import de.shop.kundenverwaltung.domain.Wartungsvertrag;
import de.shop.util.IdGroup;
import de.shop.util.Log;
import de.shop.util.Transactional;
import de.shop.util.ValidatorProvider;

/**
 * Anwendungslogik fuer die KundeService
 */
/**
 * 	@Consumes
	@RequestScoped
	@Transactional
 *
 */

@Log
public class KundeService implements Serializable {
	private static final long serialVersionUID = -5520738420154763865L;
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	public enum FetchType {
		NUR_KUNDE,
		MIT_BESTELLUNGEN,
		MIT_WARTUNGSVERTRAEGEN
	}
	
	public enum OrderType {
		KEINE,
		ID
	}
	
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

	/**
	 */
	public List<AbstractKunde> findAllKunden(FetchType fetch, OrderType order) {
		List<AbstractKunde> kunden;
		switch (fetch) {
			case NUR_KUNDE:
				kunden = OrderType.ID.equals(order)
				         ? em.createNamedQuery(AbstractKunde.FIND_KUNDEN_ORDER_BY_ID, AbstractKunde.class)
				             .getResultList()
				         : em.createNamedQuery(AbstractKunde.FIND_KUNDEN, AbstractKunde.class)
				             .getResultList();
				break;
			
			case MIT_BESTELLUNGEN:
				kunden = em.createNamedQuery(AbstractKunde.FIND_KUNDEN_FETCH_BESTELLUNGEN, AbstractKunde.class)
						   .getResultList();
				break;

			default:
				kunden = OrderType.ID.equals(order)
		                 ? em.createNamedQuery(AbstractKunde.FIND_KUNDEN_ORDER_BY_ID, AbstractKunde.class)
		                	 .getResultList()
		                 : em.createNamedQuery(AbstractKunde.FIND_KUNDEN, AbstractKunde.class)
		                     .getResultList();
				break;
		}

		return kunden;
	}
	
	/**
	 */
	public List<AbstractKunde> findKundenByNachname(String nachname, FetchType fetch, Locale locale) {
		validateNachname(nachname, locale);
		
		List<AbstractKunde> kunden;
		switch (fetch) {
			case NUR_KUNDE:
				kunden = em.createNamedQuery(AbstractKunde.FIND_KUNDEN_BY_NACHNAME, AbstractKunde.class)
						   .setParameter(AbstractKunde.PARAM_KUNDE_NACHNAME, nachname)
						   .getResultList();
				break;
			
			case MIT_BESTELLUNGEN:
				kunden = em.createNamedQuery(AbstractKunde.FIND_KUNDEN_BY_NACHNAME_FETCH_BESTELLUNGEN,
						                     AbstractKunde.class)
						   .setParameter(AbstractKunde.PARAM_KUNDE_NACHNAME, nachname)
						   .getResultList();
				break;

			default:
				kunden = em.createNamedQuery(AbstractKunde.FIND_KUNDEN_BY_NACHNAME, AbstractKunde.class)
						   .setParameter(AbstractKunde.PARAM_KUNDE_NACHNAME, nachname)
						   .getResultList();
				break;
		}

		return kunden;
	}
	
	private void validateNachname(String nachname, Locale locale) {
		final Validator validator = validatorProvider.getValidator(locale);
		final Set<ConstraintViolation<AbstractKunde>> violations = validator.validateValue(AbstractKunde.class,
				                                                                           "nachname",
				                                                                           nachname,
				                                                                           Default.class);
		if (!violations.isEmpty())
			throw new InvalidNachnameException(nachname, violations);
	}
	
	public List<String> findNachnamenByPrefix(String nachnamePrefix) {
		final List<String> nachnamen = em.createNamedQuery(AbstractKunde.FIND_NACHNAMEN_BY_PREFIX,
				                                           String.class)
				                         .setParameter(AbstractKunde.PARAM_KUNDE_NACHNAME_PREFIX, nachnamePrefix + '%')
				                         .getResultList();
		return nachnamen;
	}

	/**
	 */
	public AbstractKunde findKundeById(Long id, FetchType fetch, Locale locale) {
		validateKundeId(id, locale);
		
		AbstractKunde kunde = null;
		try {
			switch (fetch) {
				case NUR_KUNDE:
					kunde = em.find(AbstractKunde.class, id);
					break;
				
				case MIT_BESTELLUNGEN:
					kunde = em.createNamedQuery(AbstractKunde.FIND_KUNDE_BY_ID_FETCH_BESTELLUNGEN, AbstractKunde.class)
							  .setParameter(AbstractKunde.PARAM_KUNDE_ID, id)
							  .getSingleResult();
					break;
					
				case MIT_WARTUNGSVERTRAEGEN:
					kunde = em.createNamedQuery(AbstractKunde.FIND_KUNDE_BY_ID_FETCH_WARTUNGSVERTRAEGE,
							                    AbstractKunde.class)
							  .setParameter(AbstractKunde.PARAM_KUNDE_ID, id)
							  .getSingleResult();
					break;
	
				default:
					kunde = em.find(AbstractKunde.class, id);
					break;
			}
		}
		catch (NoResultException e) {
			return null;
		}

		return kunde;
	}
	
	private void validateKundeId(Long kundeId, Locale locale) {
		final Validator validator = validatorProvider.getValidator(locale);
		final Set<ConstraintViolation<AbstractKunde>> violations = validator.validateValue(AbstractKunde.class,
				                                                                           "id",
				                                                                           kundeId,
				                                                                           IdGroup.class);
		if (!violations.isEmpty())
			throw new InvalidKundeIdException(kundeId, violations);
	}

	
	public List<Long> findIdsByPrefix(String idPrefix) {
		final List<Long> ids = em.createNamedQuery(AbstractKunde.FIND_IDS_BY_PREFIX, Long.class)
				                 .setParameter(AbstractKunde.PARAM_KUNDE_ID_PREFIX, idPrefix + '%')
				                 .getResultList();
		return ids;
	}
	
	/**
	 */
	public AbstractKunde findKundeByEmail(String email, Locale locale) {
		validateEmail(email, locale);
		try {
			final AbstractKunde kunde = em.createNamedQuery(AbstractKunde.FIND_KUNDE_BY_EMAIL, AbstractKunde.class)
					                      .setParameter(AbstractKunde.PARAM_KUNDE_EMAIL, email)
					                      .getSingleResult();
			return kunde;
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	private void validateEmail(String email, Locale locale) {
		final Validator validator = validatorProvider.getValidator(locale);
		final Set<ConstraintViolation<AbstractKunde>> violations = validator.validateValue(AbstractKunde.class,
				                                                                           "email",
				                                                                           email,
				                                                                           Default.class);
		if (!violations.isEmpty())
			throw new InvalidEmailException(email, violations);
	}

	/**
	 */
	public AbstractKunde createKunde(AbstractKunde kunde, Locale locale) {
		if (kunde == null) {
			return kunde;
		}

		// Werden alle Constraints beim Einfuegen gewahrt?
		validateKunde(kunde, locale, Default.class, PasswordGroup.class);
		
		// Pruefung, ob die Email-Adresse schon existiert
		try {
			em.createNamedQuery(AbstractKunde.FIND_KUNDE_BY_EMAIL, AbstractKunde.class)
			  .setParameter(AbstractKunde.PARAM_KUNDE_EMAIL, kunde.getEmail())
			  .getSingleResult();
			throw new EmailExistsException(kunde.getEmail());
		}
		catch (NoResultException e) {
			// Noch kein Kunde mit dieser Email-Adresse
			LOGGER.trace("Email-Adresse existiert noch nicht");
		}
		
		em.persist(kunde);
		return kunde;		
	}
	
	private void validateKunde(AbstractKunde kunde, Locale locale, Class<?>... groups) {
		// Werden alle Constraints beim Einfuegen gewahrt?
		final Validator validator = validatorProvider.getValidator(locale);
		
		final Set<ConstraintViolation<AbstractKunde>> violations = validator.validate(kunde, groups);
		if (!violations.isEmpty()) {
			throw new InvalidKundeException(kunde, violations);
		}
	}
	
	/**
	 */
	public AbstractKunde updateKunde(AbstractKunde kunde, Locale locale) {
		if (kunde == null) {
			return null;
		}

		// Werden alle Constraints beim Modifizieren gewahrt?
		validateKunde(kunde, locale, Default.class, PasswordGroup.class, IdGroup.class);
		
		// kunde vom EntityManager trennen, weil anschliessend z.B. nach Id und Email gesucht wird
		em.detach(kunde);
		
		// Gibt es ein anderes Objekt mit gleicher Email-Adresse?
		final AbstractKunde	tmp = findKundeByEmail(kunde.getEmail(), locale);
		if (tmp != null) {
			em.detach(tmp);
			if (tmp.getId().longValue() != kunde.getId().longValue()) {
				// anderes Objekt mit gleichem Attributwert fuer email
				throw new EmailExistsException(kunde.getEmail());
			}
		}

		em.merge(kunde);
		return kunde;
	}

	/**
	 */
	public void deleteKunde(AbstractKunde kunde) {
		if (kunde == null) {
			return;
		}
		
		// Bestellungen laden, damit sie anschl. ueberprueft werden koennen
		try {
			kunde = findKundeById(kunde.getId(), FetchType.MIT_BESTELLUNGEN, Locale.getDefault());
		}
		catch (InvalidKundeIdException e) {
			return;
		}
		
		if (kunde == null) {
			return;
		}
		
		// Gibt es Bestellungen?
		if (!kunde.getBestellungen().isEmpty()) {
			throw new KundeDeleteBestellungException(kunde);
		}

		em.remove(kunde);
	}

	/**
	 */
	public List<AbstractKunde> findKundenByPLZ(String plz) {
		final List<AbstractKunde> kunden = em.createNamedQuery(AbstractKunde.FIND_KUNDEN_BY_PLZ, AbstractKunde.class)
                                             .setParameter(AbstractKunde.PARAM_KUNDE_ADRESSE_PLZ, plz)
                                             .getResultList();
		return kunden;
	}

	/**
	 */
	public List<AbstractKunde> findKundenBySeit(Date seit) {
		final List<AbstractKunde> kunden = em.createNamedQuery(AbstractKunde.FIND_KUNDEN_BY_DATE, AbstractKunde.class)
                                             .setParameter(AbstractKunde.PARAM_KUNDE_SEIT, seit)
                                             .getResultList();
		return kunden;
	}
	
	/**
	 */
	public List<AbstractKunde> findPrivatkundenFirmenkunden() {
		final List<AbstractKunde> kunden = em.createNamedQuery(AbstractKunde.FIND_PRIVATKUNDEN_FIRMENKUNDEN,
                                                               AbstractKunde.class)
                                             .getResultList();
		return kunden;
	}
	
	/**
	 */
	public List<AbstractKunde> findKundenByNachnameCriteria(String nachname) {
		final CriteriaBuilder builder = em.getCriteriaBuilder();
		final CriteriaQuery<AbstractKunde> criteriaQuery = builder.createQuery(AbstractKunde.class);
		final Root<AbstractKunde> k = criteriaQuery.from(AbstractKunde.class);

		final Path<String> nachnamePath = k.get(AbstractKunde_.nachname);
		//final Path<String> nachnamePath = k.get("nachname");
		
		final Predicate pred = builder.equal(nachnamePath, nachname);
		criteriaQuery.where(pred);

		// Ausgabe des komponierten Query-Strings. Voraussetzung: das Modul "org.hibernate" ist aktiviert
		//if (LOGGER.isLoggable(FINEST)) {
		//	query.unwrap(org.hibernate.Query.class).getQueryString();
		//}
		
		final List<AbstractKunde> kunden = em.createQuery(criteriaQuery).getResultList();
		return kunden;
	}
	
	/**
	 */
	public List<AbstractKunde> findKundenMitMinBestMenge(short minMenge) {
		final CriteriaBuilder builder = em.getCriteriaBuilder();
		final CriteriaQuery<AbstractKunde> criteriaQuery  = builder.createQuery(AbstractKunde.class);
		final Root<AbstractKunde> k = criteriaQuery.from(AbstractKunde.class);

		final Join<AbstractKunde, Bestellung> b = k.join(AbstractKunde_.bestellungen);
		final Join<Bestellung, Bestellposten> bp = b.join(Bestellung_.bestellposten);
		criteriaQuery.where(builder.gt(bp.<Short>get(Bestellposten_.anzahl), minMenge))
		             .distinct(true);
		
		final List<AbstractKunde> kunden = em.createQuery(criteriaQuery).getResultList();
		return kunden;
	}
	
	/**
	 */
	public List<Wartungsvertrag> findWartungsvertraege(Long kundeId) {
		final List<Wartungsvertrag> wartungsvertraege =
				                    em.createNamedQuery(Wartungsvertrag.FIND_WARTUNGSVERTRAEGE_BY_KUNDE_ID,
                                                        Wartungsvertrag.class)
                                      .setParameter(Wartungsvertrag.PARAM_KUNDE_ID, kundeId)
                                      .getResultList();
		return wartungsvertraege;
	}
	
	/**
	 */
	public Wartungsvertrag createWartungsvertrag(Wartungsvertrag wartungsvertrag,
			                                     AbstractKunde kunde) {
		if (wartungsvertrag == null || kunde == null) {
			return null;
		}
		
		try {
			kunde = findKundeById(kunde.getId(), FetchType.NUR_KUNDE, Locale.getDefault());
		}
		catch (InvalidKundeIdException e) {
			return null;
		}
		
		wartungsvertrag.setKunde(kunde);
		kunde.addWartungsvertrag(wartungsvertrag);
		
		em.persist(wartungsvertrag);
		return wartungsvertrag;
	}
}
