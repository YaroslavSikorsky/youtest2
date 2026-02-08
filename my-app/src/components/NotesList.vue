<template>
  <div class="notes-list">
    <!-- Загрузка -->
    <div v-if="loading" class="notes-loading">
      Загрузка заметок...
    </div>

    <!-- Ошибка -->
    <div v-else-if="error" class="notes-error">
      {{ error }}
      <button class="btn-retry" @click="reload">Попробовать снова</button>
    </div>

    <!-- Пустой список -->
    <div v-else-if="notes.length === 0" class="notes-empty">
      <div class="empty-icon">📝</div>
      <p class="empty-title">Заметок пока нет</p>
      <p class="empty-subtitle">Создайте первую заметку, нажав кнопку выше</p>
    </div>

    <!-- Список заметок -->
    <div v-else class="notes-grid">
      <NoteCard
          v-for="note in notes"
          :key="note.id"
          :note="note"
          @open="openNote"
      />
    </div>

    <!-- Модальное окно -->
    <NoteDetailsModal
        v-if="activeNote"
        :note="activeNote"
        @close="closeNote"
        @changed="reload"
    />
  </div>
</template>

<script>
import { getNotes } from "@/api";
import NoteCard from "./NoteCard.vue";
import NoteDetailsModal from "./NoteDetailsModal.vue";

export default {
  name: "NotesList",
  components: { NoteCard, NoteDetailsModal },

  props: {
    userId: {
      type: String,
      required: true
    },
    type: {
      type: String,
      default: ""
    },
    refreshKey: {
      type: Number,
      default: 0
    }
  },

  data() {
    return {
      notes: [],
      activeNote: null,
      loading: false,
      error: null
    };
  },

  watch: {
    userId: "reload",
    type: "reload",
    refreshKey: "reload"
  },

  mounted() {
    this.reload();
  },

  methods: {
    async reload() {
      if (!this.userId) {
        console.warn("NotesList: userId не указан");
        return;
      }

      this.loading = true;
      this.error = null;

      try {
        this.notes = await getNotes(this.userId, this.type);
      } catch (error) {
        console.error("Ошибка загрузки заметок:", error);
        this.error = "Не удалось загрузить заметки";
        this.notes = [];
      } finally {
        this.loading = false;
      }
    },

    openNote(note) {
      this.activeNote = note;
    },

    closeNote() {
      this.activeNote = null;
    }
  }
};
</script>

<style scoped>
.notes-list {
  min-height: 300px;
}

/* GRID */
.notes-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
  padding-bottom: 40px;
}

/* LOADING */
.notes-loading {
  text-align: center;
  padding: 80px 20px;
  color: var(--ui-text-muted);
  font-size: 16px;
}

/* ERROR */
.notes-error {
  text-align: center;
  padding: 60px 20px;
  color: #ff4d4f;
  font-size: 16px;
  display: flex;
  flex-direction: column;
  gap: 16px;
  align-items: center;
}

.btn-retry {
  background: var(--ui-primary);
  color: #fff;
  border: none;
  padding: 10px 24px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.2s;
}

.btn-retry:hover {
  background: #5a6fd8;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(107, 124, 255, 0.3);
}

/* EMPTY STATE */
.notes-empty {
  text-align: center;
  padding: 80px 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.empty-icon {
  font-size: 64px;
  opacity: 0.5;
  margin-bottom: 8px;
}

.empty-title {
  font-size: 20px;
  font-weight: 600;
  color: var(--ui-primary);
  margin: 0;
}

.empty-subtitle {
  font-size: 14px;
  color: var(--ui-text-muted);
  margin: 0;
}

/* MOBILE */
@media (max-width: 768px) {
  .notes-grid {
    grid-template-columns: 1fr;
    gap: 16px;
  }
}
</style>