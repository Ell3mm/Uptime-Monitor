package com.uptimemonitor.api.monitor;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MonitorRequest(
		@NotBlank @Size(max = 100) String name,
		@NotBlank @Size(max = 2_000) @Pattern(regexp = "https?://.+", message = "must be an HTTP or HTTPS URL") String url,
		@NotNull @Min(10) @Max(86_400) Integer intervalSeconds,
		@NotNull @Min(1) @Max(300) Integer timeoutSeconds,
		boolean enabled) {
}