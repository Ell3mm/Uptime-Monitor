package com.uptimemonitor.api.monitor;

import java.time.Instant;

public record MonitorResponse(
		Long id,
		String name,
		String url,
		Integer intervalSeconds,
		Integer timeoutSeconds,
		boolean enabled,
		Instant createdAt,
		String status,
		Instant lastCheckedAt,
		Integer lastStatusCode,
		Long lastResponseTimeMs,
		String lastError) {

	public static MonitorResponse from(Monitor monitor) {
		return new MonitorResponse(
				monitor.getId(),
				monitor.getName(),
				monitor.getUrl(),
				monitor.getIntervalSeconds(),
				monitor.getTimeoutSeconds(),
				monitor.isEnabled(),
				monitor.getCreatedAt(),
				monitor.getStatus(),
				monitor.getLastCheckedAt(),
				monitor.getLastStatusCode(),
				monitor.getLastResponseTimeMs(),
				monitor.getLastError());
	}
}