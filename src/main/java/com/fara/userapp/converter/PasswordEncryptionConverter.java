package com.fara.userapp.converter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class PasswordEncryptionConverter implements
		AttributeConverter<String, String> {

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public PasswordEncryptionConverter() {
		this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
	}

	@Override
	public String convertToDatabaseColumn(String rawPassword) {
		if (rawPassword == null) {
			return null;
		}
		return bCryptPasswordEncoder.encode(rawPassword);
	}

	@Override
	public String convertToEntityAttribute(String encryptedPassword) {
		return encryptedPassword;
	}
}
