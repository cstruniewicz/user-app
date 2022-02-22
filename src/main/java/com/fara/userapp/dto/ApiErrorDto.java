package com.fara.userapp.dto;

import java.time.ZonedDateTime;

public class ApiErrorDto {
	private String type;
	private String title;
	private String resourceLocation;

	public ApiErrorDto() {
	}

	public ApiErrorDto(String type, String title, String resourceLocation, ZonedDateTime timestamp) {
		this.type = type;
		this.title = title;
		this.resourceLocation = resourceLocation;
	}

	public static ApiErrorDto.ApiErrorDtoBuilder builder() {
		return new ApiErrorDto.ApiErrorDtoBuilder();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getResourceLocation() {
		return resourceLocation;
	}

	public void setResourceLocation(String resourceLocation) {
		this.resourceLocation = resourceLocation;
	}

	public static final class ApiErrorDtoBuilder {
		private String type;
		private String title;
		private String resourceLocation;

		private ApiErrorDtoBuilder() {
		}

		public ApiErrorDtoBuilder type(String type) {
			this.type = type;
			return this;
		}

		public ApiErrorDtoBuilder title(String title) {
			this.title = title;
			return this;
		}

		public ApiErrorDtoBuilder resourceLocation(String resourceLocation) {
			this.resourceLocation = resourceLocation;
			return this;
		}

		public ApiErrorDto build() {
			ApiErrorDto apiErrorDto = new ApiErrorDto();
			apiErrorDto.setType(type);
			apiErrorDto.setTitle(title);
			apiErrorDto.setResourceLocation(resourceLocation);
			return apiErrorDto;
		}
	}
}
