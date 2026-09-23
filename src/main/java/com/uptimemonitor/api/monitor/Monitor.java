package com.uptimemonitor.api.monitor;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "monitors")
public class Monitor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, length = 2_000)
	private String url;

	@Column(nullable = false)
	private Integer intervalSeconds;

	@Column(nullable = false)
	private Integer timeoutSeconds;

	@Column(nullable = false)
	private boolean enabled;

	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	private String status;

	private Instant lastCheckedAt;

	private Integer lastStatusCode;

	private Long lastResponseTimeMs;

	@Column(length = 1_000)
	private String lastError;

	protected Monitor() {
	}

	public Monitor(String name, String url, Integer intervalSeconds, Integer timeoutSeconds) {
		this.name = name;
		this.url = url;
		this.intervalSeconds = intervalSeconds;
		this.timeoutSeconds = timeoutSeconds;
		this.enabled = true;
		this.createdAt = Instant.now();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public Integer getIntervalSeconds() {
		return intervalSeconds;
	}

	public Integer getTimeoutSeconds() {
		return timeoutSeconds;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public String getStatus() {
		return status;
	}

	public Instant getLastCheckedAt() {
		return lastCheckedAt;
	}

	public Integer getLastStatusCode() {
		return lastStatusCode;
	}

	public Long getLastResponseTimeMs() {
		return lastResponseTimeMs;
	}

	public String getLastError() {
		return lastError;
	}

	public void update(String name, String url, Integer intervalSeconds, Integer timeoutSeconds, boolean enabled) {
		this.name = name;
		this.url = url;
		this.intervalSeconds = intervalSeconds;
		this.timeoutSeconds = timeoutSeconds;
		this.enabled = enabled;
	}

	public void recordCheck(CheckResultRequest result) {
		this.status = result.status();
		this.lastCheckedAt = result.checkedAt();
		this.lastStatusCode = result.statusCode();
		this.lastResponseTimeMs = result.responseTimeMs();
		this.lastError = result.error();
	}
}