package com.uptimemonitor.api.monitor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/monitors")
public class MonitorController {

	private final MonitorService monitorService;

	public MonitorController(MonitorService monitorService) {
		this.monitorService = monitorService;
	}

	@GetMapping
	public List<MonitorResponse> findAll() {
		return monitorService.findAll();
	}

	@GetMapping("/{id}")
	public MonitorResponse findById(@PathVariable Long id) {
		return monitorService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MonitorResponse create(@Valid @RequestBody MonitorRequest request) {
		return monitorService.create(request);
	}

	@PutMapping("/{id}")
	public MonitorResponse update(@PathVariable Long id, @Valid @RequestBody MonitorRequest request) {
		return monitorService.update(id, request);
	}

	@PostMapping("/{id}/checks")
	public MonitorResponse recordCheck(@PathVariable Long id, @Valid @RequestBody CheckResultRequest result) {
		return monitorService.recordCheck(id, result);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		monitorService.delete(id);
	}
}