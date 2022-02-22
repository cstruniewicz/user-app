package com.fara.userapp.converter;

import javax.persistence.AttributeConverter;

public abstract class AbstractGenericEnumConverter <E extends Enum<E>> implements AttributeConverter<E, String> {

	private final Class<E> clazz;

	protected AbstractGenericEnumConverter(Class<E> clazz) {
		this.clazz = clazz;
	}

	@Override
	public String convertToDatabaseColumn(E enumValue) {
		if (enumValue == null) {
			return null;
		}
		return enumValue.name();

	}

	@Override
	public E convertToEntityAttribute(String enumName) {
		if (enumName == null) {
			return null;
		}
		return Enum.valueOf(clazz, enumName);
	}
}
