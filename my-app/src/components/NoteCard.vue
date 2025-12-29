<template>
  <div class="note-card">
    <div class="note-card__header">
      <div class="type-block">
        <span class="note-card__dot" :class="dotClass"></span>
        <span class="type-text">{{ note.type }}</span>
      </div>

      <div class="actions">
        <button class="btn icon-btn" @click="startEdit" title="Редактировать">✏️</button>
        <button class="btn icon-btn btn--ghost delete" @click="deleteNote" title="Удалить">🗑️</button>
      </div>
    </div>

    <div class="note-card__content">
      <div v-if="!isEditing" class="note-card__text" @click="startEdit">{{ note.text }}</div>

      <div v-else class="edit-block">
        <textarea v-model="editText" class="edit-textarea"></textarea>
        <div class="edit-actions">
          <button class="btn btn--primary" @click="saveEdit">Сохранить</button>
          <button class="btn btn--ghost" @click="cancelEdit">Отмена</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "NoteCard",
  props: {note: Object},
  data() {
    return {
      isEditing: false,
      editText: ""
    };
  },
  computed: {
    dotClass() {
      const t = (this.note.type || "").toLowerCase();
      return {
        "note-card__dot--art": t === "art",
        "note-card__dot--body": t === "body",
        "note-card__dot--mind": t === "mind"
      };
    }
  },
  methods: {
    startEdit() {
      this.isEditing = true;
      this.editText = this.note.text;
    },
    cancelEdit() {
      this.isEditing = false;
    },
    async saveEdit() {
      await fetch(`http://localhost:8087/notes/${this.note.id}`, {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
          text: this.editText,
          type: this.note.type
        })
      });

      this.isEditing = false;
      this.$emit("updated");
    },
    async deleteNote() {
      await fetch(`http://localhost:8087/notes/${this.note.id}`, {
        method: "DELETE"
      });

      this.$emit("updated");
    }
  }
};
</script>

<style scoped>
.note-card {
  background: var(--ui-surface);
  border-radius: 16px;
  padding: 18px;
  border: 1px solid var(--ui-border);
  box-shadow: var(--shadow-sm);
  transition: transform 0.18s, box-shadow 0.18s;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.note-card:hover {
  transform: translateY(-3px);
  box-shadow: var(--shadow-lg);
}

/* HEADER */
.note-card__header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.type-block {
  display: flex;
  align-items: center;
  gap: 10px;
}

/* TYPE TEXT */
.type-text {
  font-size: 14px;
  font-weight: 700;
  color: var(--ui-text-muted);
}

/* DOT COLORS */
.note-card__dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background: var(--tag-muted);
}

.note-card__dot--art {
  background: #d7c3ff;
}

.note-card__dot--body {
  background: #b8f3d9;
}

.note-card__dot--mind {
  background: #c2d9ff;
}

/* ACTION BUTTONS — улучшенные */
.actions {
  display: flex;
  gap: 8px;
}

.icon-btn {
  width: 32px;
  height: 32px;
  background: var(--ui-surface);
  border: 1px solid var(--ui-border);
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all .2s ease;
  box-shadow: var(--shadow-sm);
}

.icon-btn:hover {
  background: var(--ui-primary);
  color: #fff;
  border-color: var(--ui-primary);
  box-shadow: 0 8px 20px rgba(107, 124, 255, 0.25);
}

.icon-btn.delete:hover {
  background: #ff6262;
  border-color: #ff6262;
  color: white;
  box-shadow: 0 8px 20px rgba(255, 54, 54, 0.25);
}

.icon {
  font-size: 16px;
}

/* TEXT */
.note-card__text {
  font-size: 15px;
  color: var(--ui-text);
  line-height: 1.55;
  white-space: pre-wrap;
}

/* EDIT MODE */
.edit-block {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.edit-textarea {
  width: 100%;
  padding: 12px;
  border-radius: 12px;
  border: 1px solid var(--ui-border);
  resize: vertical;
  background: #fff;
  min-height: 120px;
  font-size: 15px;
  line-height: 1.5;
}

.edit-actions {
  display: flex;
  gap: 10px;
}

.save-btn {
  padding: 8px 14px;
  border-radius: 10px;
  background: var(--ui-primary);
  color: white;
  border: none;
  cursor: pointer;
  transition: 0.2s;
}

.save-btn:hover {
  opacity: 0.9;
}

.cancel-btn {
  padding: 8px 14px;
  border-radius: 10px;
  background: #ddd;
  border: none;
  cursor: pointer;
}

.cancel-btn:hover {
  background: #ccc;
}
</style>
