type Monitor = {
  id: number;
  name: string;
  url: string;
  intervalSeconds: number;
  timeoutSeconds: number;
  enabled: boolean;
  status: string | null;
  lastCheckedAt: string | null;
  lastStatusCode: number | null;
  lastResponseTimeMs: number | null;
  lastError: string | null;
};

const API_URL = "http://localhost:8080/api";
const monitorList = document.querySelector<HTMLDivElement>("#monitors")!;
const form = document.querySelector<HTMLFormElement>("#monitor-form")!;
const formMessage = document.querySelector<HTMLParagraphElement>("#form-message")!;

function loadMonitors() {
  return fetch(`${API_URL}/monitors`).then((response) => {
    if (!response.ok) throw new Error("The API could not be reached.");
    return response.json() as Promise<Monitor[]>;
  });
}

function render(monitors: Monitor[]) {
  if (monitors.length === 0) {
    monitorList.innerHTML = '<p class="message">No monitors yet. Add your first service.</p>';
    return;
  }

  monitorList.innerHTML = monitors.map((monitor) => {
    const status = monitor.status ?? "PENDING";
    const detail = monitor.lastCheckedAt
      ? `${monitor.lastResponseTimeMs ?? "-"} ms · checked ${new Date(monitor.lastCheckedAt).toLocaleTimeString()}`
      : "Waiting for the first check";
    return `<article class="monitor-card">
      <div><span class="status status-${status.toLowerCase()}">${status}</span><h3>${escapeHtml(monitor.name)}</h3><a href="${escapeHtml(monitor.url)}" target="_blank" rel="noreferrer">${escapeHtml(monitor.url)}</a></div>
      <p>${detail}</p>
    </article>`;
  }).join("");
}

function escapeHtml(value: string) {
  const replacements: Record<string, string> = { "&": "&amp;", "<": "&lt;", ">": "&gt;", "'": "&#39;", '"': "&quot;" };
  return value.replace(/[&<>'"]/g, (character) => replacements[character] ?? character);
}

async function refresh() {
  try {
    render(await loadMonitors());
  } catch (error) {
    monitorList.innerHTML = `<p class="message error">${(error as Error).message} Start the Spring Boot API first.</p>`;
  }
}

form.addEventListener("submit", async (event) => {
  event.preventDefault();
  const data = new FormData(form);
  const payload = {
    name: data.get("name"),
    url: data.get("url"),
    intervalSeconds: Number(data.get("intervalSeconds")),
    timeoutSeconds: Number(data.get("timeoutSeconds")),
    enabled: true,
  };
  try {
    const response = await fetch(`${API_URL}/monitors`, { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify(payload) });
    if (!response.ok) throw new Error("Please check the monitor details.");
    form.reset();
    formMessage.textContent = "Monitor added.";
    await refresh();
  } catch (error) {
    formMessage.textContent = (error as Error).message;
  }
});

document.querySelector<HTMLButtonElement>("#refresh")!.addEventListener("click", refresh);
refresh();
