package com.uptimemonitor.api.monitor;

import java.time.Instant;

public record MonitorResponse(
		Long id,
		String name,
		String url,
		Integer intervalSeconds,
		Integer timeoutSeconds,
		boolean enabled,
		Instant createdAt) {

	public static MonitorResponse from(Monitor monitor) {
		return new MonitorResponse(
				monitor.getId(),
				monitor.getName(),
				monitor.getUrl(),
				monitor.getIntervalSeconds(),
				monitor.getTimeoutSeconds(),
				monitor.isEnabled(),
				monitor.getCreatedAt());
	}
}