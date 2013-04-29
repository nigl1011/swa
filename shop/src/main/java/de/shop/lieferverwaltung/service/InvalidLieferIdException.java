package de.shop.lieferverwaltung.service;

import java.util.Collection;

import javax.validation.ConstraintViolation;

import de.shop.lieferverwaltung.domain.Lieferung;
import de.shop.lieferverwaltung.service.LieferungValidationException;

public class InvalidLieferIdException extends LieferungValidationException {
	private static final long serialVersionUID = 1L;
	private final Long lieferId;
	
	public InvalidLieferIdException(Long lieferId, Collection<ConstraintViolation<Lieferung>> violations) {
		super(violations);
		this.lieferId = lieferId;
	}

	public Long getLieferId() {
		return lieferId;
	}

}