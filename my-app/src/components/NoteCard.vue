<template>
  <div class="note-card" @click="$emit('open', note)">
    <!-- Header -->
    <div class="note-card__header">
      <span class="note-card__dot" :class="statusClass"></span>
      <div class="note-card__title">{{ note.title || note.type }}</div>

      <!-- Индикаторы -->
      <div class="note-card__badges">
        <span v-if="note.done" class="badge badge--done" title="Выполнено">✓</span>
        <span v-if="note.calendar" class="badge badge--calendar" title="В календаре">📅</span>
      </div>
    </div>

    <!-- Text -->
    <div class="note-card__text">{{ note.text }}</div>

    <!-- Footer -->
    <div class="note-card__footer">
      <span class="note-card__type">{{ note.type }}</span>
      <span class="note-card__date">{{ formattedDate }}</span>
    </div>

    <!-- Status Bar -->
    <div class="note-card__status-bar" :class="statusClass"></div>
  </div>
</template>

<script>
export default {
  name: "NoteCard",
  props: {
    note: {
      type: Object,
      required: true
    }
  },

  computed: {
    formattedDate() {
      if (!this.note.createdAt) return "Без даты";

      const date = new Date(this.note.createdAt);
      const now = new Date();
      const diffDays = Math.floor((now - date) / (1000 * 60 * 60 * 24));

      if (diffDays === 0) return "Сегодня";
      if (diffDays === 1) return "Вчера";
      if (diffDays < 7) return `${diffDays} дн. назад`;

      return date.toLocaleDateString("ru-RU", {
        day: "2-digit",
        month: "2-digit"
      });
    },

    statusClass() {
      const status = this.note.status || 'TODO';
      return `status--${status.toLowerCase()}`;
    }
  }
};
</script>

<style scoped>
.note-card {
  background: var(--ui-surface);
  border-radius: 12px;
  padding: 18px 20px;
  border: 1px solid var(--ui-border);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
  transition: all 0.2s ease;
  display: flex;
  flex-direction: column;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.note-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(107, 124, 255, 0.15);
  border-color: var(--ui-primary);
}

/* HEADER */
.note-card__header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  gap: 10px;
}

.note-card__dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  flex-shrink: 0;
  transition: all 0.2s;
}

.note-card__dot.status--todo {
  background: #fbbf24;
}

.note-card__dot.status--in_progress {
  background: #3b82f6;
  animation: pulse 2s infinite;
}

.note-card__dot.status--done {
  background: #10b981;
}

@keyframes pulse {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.5; }
}

.note-card__title {
  font-weight: 700;
  font-size: 16px;
  color: var(--ui-text);
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.note-card__badges {
  display: flex;
  gap: 6px;
  flex-shrink: 0;
}

.badge {
  font-size: 14px;
  padding: 2px 6px;
  border-radius: 6px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
}

.badge--done {
  background: #d1fae5;
  color: #065f46;
}

.badge--calendar {
  background: #dbeafe;
  font-size: 12px;
}

/* TEXT */
.note-card__text {
  font-size: 14px;
  color: var(--ui-text-muted);
  line-height: 1.5;
  margin-bottom: 14px;
  max-height: 65px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}

/* FOOTER */
.note-card__footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 12px;
  color: var(--ui-text-muted);
  gap: 8px;
}

.note-card__type {
  font-weight: 600;
  background: rgba(107, 124, 255, 0.1);
  color: var(--ui-primary);
  padding: 4px 10px;
  border-radius: 6px;
  font-size: 11px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.note-card__date {
  font-style: italic;
  white-space: nowrap;
}

/* STATUS BAR */
.note-card__status-bar {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 3px;
}

.note-card__status-bar.status--todo {
  background: linear-gradient(90deg, #fbbf24, #f59e0b);
}

.note-card__status-bar.status--in_progress {
  background: linear-gradient(90deg, #3b82f6, #2563eb);
}

.note-card__status-bar.status--done {
  background: linear-gradient(90deg, #10b981, #059669);
}

/* MOBILE */
@media (max-width: 640px) {
  .note-card {
    padding: 16px;
  }

  .note-card__title {
    font-size: 15px;
  }

  .note-card__text {
    font-size: 13px;
    max-height: 60px;
  }
}
</style>