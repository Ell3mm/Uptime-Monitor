package com.uptimemonitor.api.monitor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MonitorService {

	private final MonitorRepository monitorRepository;

	public MonitorService(MonitorRepository monitorRepository) {
		this.monitorRepository = monitorRepository;
	}

	public List<MonitorResponse> findAll() {
		return monitorRepository.findAll().stream().map(MonitorResponse::from).toList();
	}

	public MonitorResponse findById(Long id) {
		return MonitorResponse.from(findMonitor(id));
	}

	public MonitorResponse create(MonitorRequest request) {
		Monitor monitor = new Monitor(
				request.name(),
				request.url(),
				request.intervalSeconds(),
				request.timeoutSeconds());
		monitor.update(request.name(), request.url(), request.intervalSeconds(), request.timeoutSeconds(), request.enabled());
		return MonitorResponse.from(monitorRepository.save(monitor));
	}

	public MonitorResponse update(Long id, MonitorRequest request) {
		Monitor monitor = findMonitor(id);
		monitor.update(request.name(), request.url(), request.intervalSeconds(), request.timeoutSeconds(), request.enabled());
		return MonitorResponse.from(monitorRepository.save(monitor));
	}

	public void delete(Long id) {
		Monitor monitor = findMonitor(id);
		monitorRepository.delete(monitor);
	}

	public MonitorResponse recordCheck(Long id, CheckResultRequest result) {
		Monitor monitor = findMonitor(id);
		monitor.recordCheck(result);
		return MonitorResponse.from(monitorRepository.save(monitor));
	}

	private Monitor findMonitor(Long id) {
		return monitorRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Monitor not found"));
	}
}