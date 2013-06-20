package de.shop.artikelverwaltung.rest;

import de.shop.artikelverwaltung.service.AbstractArtikelServiceException;

public class InvalidDateException extends AbstractArtikelServiceException {
	private static final long serialVersionUID = -5263812273704607795L;

		private final String invalidDate;
		
		public InvalidDateException(String invalidDate) {
			super("Ungueltiges Datum: " + invalidDate);
			this.invalidDate = invalidDate;
		}
		
		public InvalidDateException(String invalidDate, Exception e) {
			super("Ungueltiges Datum: " + invalidDate, e);
			this.invalidDate = invalidDate;
		}
		
		public String getInvalidDate() {
			return invalidDate;
		}
	}
