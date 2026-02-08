// src/api.js

export const API_URL = "http://localhost:8087";

/**
 * Получение списка заметок.
 * @param {string} userId - ID пользователя
 * @param {string} type — фильтр по типу заметки (опционально)
 * @returns {Array} — массив заметок
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
 * @param {string} id - ID заметки
 * @returns {Object} — объект заметки
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
 * @param {Object} payload — данные заметки: {title, text, type, userId}
 * @returns {Object} — ответ сервера
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
 * @param {string} id - ID заметки
 * @param {Object} payload — данные для обновления: {title, text, type, status, done, calendar, calendarDate}
 * @returns {Object} — ответ сервера
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
 * @param {string} id - ID заметки
 * @param {string} status - Новый статус (TODO, IN_PROGRESS, DONE)
 * @returns {Object} — ответ сервера
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
 * @param {string} id - ID заметки
 * @returns {Object} — ответ сервера
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
 * @returns {Array} — массив типов
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
 * @param {string} userId - ID пользователя
 * @returns {Array} — массив заметок с calendar = true
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
 * @param {string} userId - ID пользователя
 * @param {string} date - Дата в формате YYYY-MM-DD
 * @returns {Array} — массив заметок на указанную дату
 */
export async function getNotesByDate(userId, date) {
    const res = await fetch(`${API_URL}/notes/calendar/date?userId=${userId}&date=${date}`);

    if (!res.ok) {
        throw new Error("Ошибка при получении заметок по дате");
    }

    const text = await res.text();
    return text ? JSON.parse(text) : [];
}