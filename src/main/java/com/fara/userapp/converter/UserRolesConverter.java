package com.fara.userapp.converter;

import com.fara.userapp.enums.UserRolesEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserRolesConverter extends AbstractGenericEnumConverter<UserRolesEnum>
		implements AttributeConverter<UserRolesEnum, String> {

	public UserRolesConverter() {
		super(UserRolesEnum.class);
	}
}
