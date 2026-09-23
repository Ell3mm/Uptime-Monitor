"""Poll the Spring Boot API and record HTTP health checks."""

import json
import os
import time
from datetime import datetime, timezone
from urllib.error import HTTPError, URLError
from urllib.request import Request, urlopen

API_URL = os.getenv("UPTIME_API_URL", "http://localhost:8080/api")
POLL_SECONDS = int(os.getenv("UPTIME_WORKER_POLL_SECONDS", "30"))


def request_json(url, method="GET", payload=None):
    body = json.dumps(payload).encode("utf-8") if payload is not None else None
    request = Request(url, data=body, method=method, headers={"Content-Type": "application/json"})
    with urlopen(request, timeout=15) as response:
        return json.loads(response.read().decode("utf-8"))


def check_monitor(monitor):
    started = time.perf_counter()
    status = "UP"
    status_code = None
    error = None

    try:
        request = Request(monitor["url"], method="GET", headers={"User-Agent": "uptime-monitor-worker/1.0"})
        with urlopen(request, timeout=monitor["timeoutSeconds"]) as response:
            status_code = response.status
            if status_code >= 400:
                status = "DOWN"
    except (HTTPError, URLError, TimeoutError, ValueError) as exception:
        status = "DOWN"
        error = str(exception)[:1_000]

    return {
        "status": status,
        "checkedAt": datetime.now(timezone.utc).isoformat(),
        "statusCode": status_code,
        "responseTimeMs": round((time.perf_counter() - started) * 1_000),
        "error": error,
    }


def run_once():
    monitors = request_json(f"{API_URL}/monitors")
    for monitor in monitors:
        if monitor["enabled"]:
            result = check_monitor(monitor)
            request_json(f"{API_URL}/monitors/{monitor['id']}/checks", "POST", result)
            print(f"{monitor['name']}: {result['status']} ({result['responseTimeMs']}ms)", flush=True)


def main():
    while True:
        try:
            run_once()
        except Exception as exception:
            print(f"Worker error: {exception}", flush=True)
        time.sleep(POLL_SECONDS)


if __name__ == "__main__":
    main()
