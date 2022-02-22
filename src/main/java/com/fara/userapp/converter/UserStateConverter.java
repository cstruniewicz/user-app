package com.fara.userapp.converter;

import com.fara.userapp.enums.UserStateEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserStateConverter extends AbstractGenericEnumConverter<UserStateEnum>
		implements AttributeConverter<UserStateEnum, String>  {

	public UserStateConverter() {
		super(UserStateEnum.class);
	}
}
