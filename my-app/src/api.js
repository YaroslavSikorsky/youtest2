// src/api.js
export const API_URL = "http://localhost:8087";

export async function getNotes(type = "") {
    const url = `${API_URL}/notes${type ? `?type=${encodeURIComponent(type)}` : ""}`;
    const res = await fetch(url);
    if (!res.ok) throw new Error("Ошибка при получении заметок");
    return res.json();
}

export async function addNote(payload) {
    const res = await fetch(`${API_URL}/notes/add`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(payload),
    });
    if (!res.ok) throw new Error("Ошибка при добавлении заметки");
    return res.json();
}

export async function getTypes() {
    const res = await fetch(`${API_URL}/notes/type`);
    if (!res.ok) throw new Error("Ошибка при получении типов");
    return res.json();
}
