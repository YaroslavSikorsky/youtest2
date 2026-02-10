// src/api.js

// Используем прямой URL бэкенда
export const API_URL = "https://youtest2-production.up.railway.app";

console.log("API URL:", API_URL);

/**
 * Получение списка заметок.
 */
export async function getNotes(userId, type = "") {
    const params = new URLSearchParams({ userId });
    if (type) params.append("type", type);

    const url = `${API_URL}/notes?${params.toString()}`;
    const res = await fetch(url);

    if (!res.ok) {
        throw new Error("Ошибка при получении заметок");
    }

    const text = await res.text();
    return text ? JSON.parse(text) : [];
}

/**
 * Получение заметки по ID
 */
export async function getNoteById(id) {
    const res = await fetch(`${API_URL}/notes/${id}`);

    if (!res.ok) {
        throw new Error("Ошибка при получении заметки");
    }

    const text = await res.text();
    return text ? JSON.parse(text) : null;
}

/**
 * Добавление новой заметки
 */
export async function addNote(payload) {
    const res = await fetch(`${API_URL}/notes/add`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
    });

    if (!res.ok) {
        throw new Error("Ошибка при добавлении заметки");
    }

    const text = await res.text();
    return text || "OK";
}

/**
 * Обновление заметки
 */
export async function updateNote(id, payload) {
    const res = await fetch(`${API_URL}/notes/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
    });

    if (!res.ok) {
        throw new Error("Ошибка при обновлении заметки");
    }

    const text = await res.text();
    return text || "OK";
}

/**
 * Обновление статуса заметки (для drag & drop)
 */
export async function updateNoteStatus(id, status) {
    const res = await fetch(`${API_URL}/notes/${id}/status`, {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ status }),
    });

    if (!res.ok) {
        throw new Error("Ошибка при обновлении статуса");
    }

    const text = await res.text();
    return text || "OK";
}

/**
 * Удаление заметки
 */
export async function deleteNote(id) {
    const res = await fetch(`${API_URL}/notes/${id}`, {
        method: "DELETE",
    });

    if (!res.ok) {
        throw new Error("Ошибка при удалении заметки");
    }

    const text = await res.text();
    return text || "OK";
}

/**
 * Получение списка типов заметок
 */
export async function getTypes() {
    const res = await fetch(`${API_URL}/notes/type`);

    if (!res.ok) {
        throw new Error("Ошибка при получении типов");
    }

    const text = await res.text();
    return text ? JSON.parse(text) : [];
}

/**
 * Получение заметок для календаря
 */
export async function getCalendarNotes(userId) {
    const res = await fetch(`${API_URL}/notes/calendar?userId=${userId}`);

    if (!res.ok) {
        throw new Error("Ошибка при получении заметок календаря");
    }

    const text = await res.text();
    return text ? JSON.parse(text) : [];
}

/**
 * Получение заметок на конкретную дату
 */
export async function getNotesByDate(userId, date) {
    const res = await fetch(`${API_URL}/notes/calendar/date?userId=${userId}&date=${date}`);

    if (!res.ok) {
        throw new Error("Ошибка при получении заметок по дате");
    }

    const text = await res.text();
    return text ? JSON.parse(text) : [];
}

// ========== ПОЛЬЗОВАТЕЛИ ==========

/**
 * Регистрация пользователя
 */
export async function registerUser(email, password) {
    const res = await fetch(`${API_URL}/notes/users`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
    });

    if (!res.ok) {
        const text = await res.text();
        throw new Error(text || "Ошибка регистрации");
    }

    return await res.text();
}

/**
 * Логин пользователя
 */
export async function loginUser(email, password) {
    const res = await fetch(`${API_URL}/notes/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
    });

    if (!res.ok) {
        const text = await res.text();
        throw new Error(text || "Ошибка входа");
    }

    return await res.json();
}