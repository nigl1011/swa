package de.shop.bestellverwaltung.service;


	import java.util.Collection;

	import javax.validation.ConstraintViolation;

	import de.shop.bestellverwaltung.domain.Bestellung;
	import de.shop.bestellverwaltung.service.AbstractBestellungValidationException;

	public class InvalidBestellungIdException extends AbstractBestellungValidationException {
		private static final long serialVersionUID = -8973151010781329074L;
		
		private final Long bestellungId;
		
		public InvalidBestellungIdException(Long bestellungId, Collection<ConstraintViolation<Bestellung>> violations) {
			super(violations);
			this.bestellungId = bestellungId;
		}

		public Long getBestellungId() {
			return bestellungId;
		}

	}

