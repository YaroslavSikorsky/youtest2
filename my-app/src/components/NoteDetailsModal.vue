<template>
  <div class="modal-backdrop" @click.self="$emit('close')">
    <div class="modal-card">

      <!-- HEADER -->
      <header class="modal-header">
        <h2 class="modal-title">{{ localNote.title || localNote.type }}</h2>
        <div class="modal-actions">
          <button class="btn-icon" @click="toggleEdit" :title="editing ? 'Отменить' : 'Редактировать'">
            {{ editing ? '✕' : '✏︎' }}
          </button>
          <button class="btn-icon btn-danger" @click="remove" title="Удалить">⌫</button>
        </div>
      </header>

      <!-- CONTENT -->
      <div class="modal-content">
        <!-- Редактирование заголовка -->
        <input
            v-if="editing"
            v-model="localNote.title"
            type="text"
            class="edit-input"
            placeholder="Заголовок заметки"
        />

        <!-- Редактирование текста -->
        <textarea
            v-if="editing"
            v-model="localNote.text"
            class="edit-textarea"
            placeholder="Текст заметки"
        ></textarea>

        <!-- Просмотр текста -->
        <p v-else class="modal-text">
          {{ localNote.text }}
        </p>

        <!-- Мета-информация -->
        <div class="modal-meta">
          <!-- Чекбокс "В календарь" -->
          <label class="check" v-if="visibleElements.includes('calendar')">
            <input type="checkbox" v-model="localNote.calendar" @change="onCalendarChange" />
            В календарь
          </label>

          <!-- Выбор даты (только если включен календарь) -->
          <input
              v-if="localNote.calendar && visibleElements.includes('calendarDate')"
              type="date"
              v-model="localNote.calendarDate"
              class="input-date"
          />

          <!-- Чекбокс "Сделано" -->
          <label class="check" v-if="visibleElements.includes('done')">
            <input type="checkbox" v-model="localNote.done" />
            Сделано
          </label>

          <!-- Дата создания -->
          <span class="modal-date">{{ formattedDate }}</span>
        </div>
      </div>

      <!-- FOOTER (кнопки сохранения при редактировании) -->
      <footer v-if="editing" class="modal-footer">
        <button class="btn btn-primary" @click="save" :disabled="saving">
          {{ saving ? 'Сохранение...' : 'Сохранить' }}
        </button>
        <button class="btn btn-ghost" @click="cancel">Отмена</button>
      </footer>

    </div>
  </div>
</template>

<script>
import { updateNote, deleteNote } from "@/api";

export default {
  name: "NoteDetailsModal",
  props: {
    note: {
      type: Object,
      required: true
    }
  },

  data() {
    const settings = JSON.parse(localStorage.getItem("settings")) || {};
    const visibleElements = settings.visibleElements || ["done", "calendar", "calendarDate"];

    return {
      editing: false,
      saving: false,
      localNote: {
        ...this.note,
        // Устанавливаем значения по умолчанию, если их нет
        done: this.note.done ?? false,
        calendar: this.note.calendar ?? false,
        calendarDate: this.note.calendarDate || new Date().toISOString().substr(0, 10),
        status: this.note.status || 'TODO'
      },
      originalNote: null, // для отмены изменений
      visibleElements
    };
  },

  computed: {
    formattedDate() {
      if (!this.localNote.createdAt) return "Без даты";

      return new Date(this.localNote.createdAt)
          .toLocaleDateString("ru-RU", {
            day: "2-digit",
            month: "2-digit",
            year: "numeric",
            hour: "2-digit",
            minute: "2-digit"
          });
    }
  },

  methods: {
    toggleEdit() {
      if (this.editing) {
        this.cancel();
      } else {
        // Сохраняем оригинальное состояние для отмены
        this.originalNote = JSON.parse(JSON.stringify(this.localNote));
        this.editing = true;
      }
    },

    onCalendarChange() {
      // Если календарь выключен, очищаем дату
      if (!this.localNote.calendar) {
        this.localNote.calendarDate = null;
      } else {
        // Если включен, но даты нет - ставим текущую
        if (!this.localNote.calendarDate) {
          this.localNote.calendarDate = new Date().toISOString().substr(0, 10);
        }
      }
    },

    async save() {
      // Валидация
      if (!this.localNote.title?.trim()) {
        alert("Введите заголовок заметки");
        return;
      }
      if (!this.localNote.text?.trim()) {
        alert("Введите текст заметки");
        return;
      }

      this.saving = true;

      try {
        // Подготавливаем данные для отправки
        const payload = {
          title: this.localNote.title.trim(),
          text: this.localNote.text.trim(),
          type: this.localNote.type,
          status: this.localNote.status,
          done: this.localNote.done,
          calendar: this.localNote.calendar,
          calendarDate: this.localNote.calendar ? this.localNote.calendarDate : null
        };

        await updateNote(this.localNote.id, payload);

        this.editing = false;
        this.originalNote = null;

        // Уведомляем родительский компонент об изменениях
        this.$emit("changed");
      } catch (error) {
        console.error("Ошибка при сохранении заметки:", error);
        alert("Не удалось сохранить заметку. Попробуйте еще раз.");
      } finally {
        this.saving = false;
      }
    },

    cancel() {
      // Восстанавливаем оригинальное состояние
      if (this.originalNote) {
        this.localNote = JSON.parse(JSON.stringify(this.originalNote));
        this.originalNote = null;
      }
      this.editing = false;
    },

    async remove() {
      if (!confirm("Удалить запись? Это действие нельзя отменить.")) {
        return;
      }

      try {
        await deleteNote(this.localNote.id);

        // Уведомляем родительский компонент
        this.$emit("changed");
        this.$emit("close");
      } catch (error) {
        console.error("Ошибка при удалении заметки:", error);
        alert("Не удалось удалить заметку. Попробуйте еще раз.");
      }
    }
  }
};
</script>

<style scoped>
.modal-backdrop {
  position: fixed;
  inset: 0;
  background: rgba(13, 18, 36, 0.6);
  backdrop-filter: blur(4px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  animation: fadeIn 0.2s ease;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

.modal-card {
  background: var(--ui-surface);
  border-radius: var(--ui-radius-lg);
  padding: 24px 28px;
  width: 100%;
  max-width: 580px;
  max-height: 85vh;
  overflow-y: auto;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  display: flex;
  flex-direction: column;
  gap: 20px;
  animation: slideUp 0.3s ease;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* HEADER */
.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 16px;
}

.modal-title {
  font-size: 24px;
  font-weight: 700;
  color: var(--ui-primary);
  margin: 0;
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.modal-actions {
  display: flex;
  gap: 8px;
}

.btn-icon {
  background: transparent;
  border: none;
  font-size: 20px;
  cursor: pointer;
  padding: 6px 10px;
  border-radius: 6px;
  transition: background 0.2s;
  color: var(--ui-primary);
}

.btn-icon:hover {
  background: rgba(107, 124, 255, 0.1);
}

.btn-icon.btn-danger {
  color: #ff4d4f;
}

.btn-icon.btn-danger:hover {
  background: rgba(255, 77, 79, 0.1);
}

/* CONTENT */
.modal-content {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.edit-input {
  width: 100%;
  padding: 12px 14px;
  border-radius: 10px;
  border: 2px solid var(--ui-border);
  font-size: 18px;
  font-weight: 600;
  color: var(--ui-primary);
  background: var(--ui-bg);
  transition: border-color 0.2s;
}

.edit-input:focus {
  outline: none;
  border-color: var(--ui-primary);
}

.modal-text {
  font-size: 16px;
  line-height: 1.6;
  color: var(--ui-text);
  white-space: pre-wrap;
  word-wrap: break-word;
  margin: 0;
}

.edit-textarea {
  width: 100%;
  min-height: 150px;
  padding: 12px 14px;
  border-radius: 10px;
  border: 2px solid var(--ui-border);
  font-size: 16px;
  line-height: 1.6;
  resize: vertical;
  font-family: inherit;
  background: var(--ui-bg);
  color: var(--ui-text);
  transition: border-color 0.2s;
}

.edit-textarea:focus {
  outline: none;
  border-color: var(--ui-primary);
}

.modal-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 14px;
  padding-top: 8px;
  font-size: 14px;
  color: var(--ui-text-muted);
  align-items: center;
  border-top: 1px solid var(--ui-border);
}

.check {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  user-select: none;
  padding: 6px 10px;
  border-radius: 6px;
  transition: background 0.2s;
}

.check:hover {
  background: rgba(107, 124, 255, 0.05);
}

.check input[type="checkbox"] {
  cursor: pointer;
  width: 18px;
  height: 18px;
}

.input-date {
  padding: 6px 10px;
  border-radius: 6px;
  border: 1px solid var(--ui-border);
  font-size: 14px;
  background: var(--ui-bg);
  color: var(--ui-text);
  cursor: pointer;
  transition: border-color 0.2s;
}

.input-date:focus {
  outline: none;
  border-color: var(--ui-primary);
}

/* DATE */
.modal-date {
  margin-left: auto;
  font-size: 13px;
  color: var(--ui-text-muted);
  font-style: italic;
}

/* FOOTER */
.modal-footer {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
  padding-top: 8px;
  border-top: 1px solid var(--ui-border);
}

.btn {
  padding: 10px 20px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 15px;
  font-weight: 500;
  transition: all 0.2s;
  border: none;
}

.btn-primary {
  background: var(--ui-primary);
  color: #fff;
}

.btn-primary:hover:not(:disabled) {
  background: #5a6fd8;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(107, 124, 255, 0.3);
}

.btn-primary:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-ghost {
  background: transparent;
  border: 1px solid var(--ui-border);
  color: var(--ui-text);
}

.btn-ghost:hover {
  background: var(--ui-bg);
  border-color: var(--ui-primary);
}

/* MOBILE */
@media (max-width: 640px) {
  .modal-card {
    max-width: calc(100% - 32px);
    padding: 20px;
  }

  .modal-title {
    font-size: 20px;
  }

  .edit-textarea {
    min-height: 120px;
  }
}
</style>