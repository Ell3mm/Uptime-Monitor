package com.uptimemonitor.api.monitor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CheckResultRequest(
		@NotBlank String status,
		@NotNull Instant checkedAt,
		Integer statusCode,
		Long responseTimeMs,
		String error) {
}