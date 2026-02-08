<template>
  <div class="kanban">
    <!-- TODO -->
    <div class="kanban-column">
      <div class="kanban-header">
        <h3 class="kanban-title">📋 Запланировано</h3>
        <span class="kanban-count">{{ columns.TODO.length }}</span>
      </div>

      <div
          class="kanban-cards"
          ref="todoColumn"
          data-status="TODO"
      >
        <NoteCard
            v-for="note in columns.TODO"
            :key="note.id"
            :note="note"
            :data-id="note.id"
            class="kanban-card-item"
            @open="openNote"
        />

        <div v-show="columns.TODO.length === 0" class="kanban-empty">
          Перетащите задачи сюда
        </div>
      </div>
    </div>

    <!-- IN_PROGRESS -->
    <div class="kanban-column">
      <div class="kanban-header">
        <h3 class="kanban-title">⚡ В работе</h3>
        <span class="kanban-count">{{ columns.IN_PROGRESS.length }}</span>
      </div>

      <div
          class="kanban-cards"
          ref="progressColumn"
          data-status="IN_PROGRESS"
      >
        <NoteCard
            v-for="note in columns.IN_PROGRESS"
            :key="note.id"
            :note="note"
            :data-id="note.id"
            class="kanban-card-item"
            @open="openNote"
        />

        <div v-show="columns.IN_PROGRESS.length === 0" class="kanban-empty">
          Перетащите задачи сюда
        </div>
      </div>
    </div>

    <!-- DONE -->
    <div class="kanban-column">
      <div class="kanban-header">
        <h3 class="kanban-title">✅ Готово</h3>
        <span class="kanban-count">{{ columns.DONE.length }}</span>
      </div>

      <div
          class="kanban-cards"
          ref="doneColumn"
          data-status="DONE"
      >
        <NoteCard
            v-for="note in columns.DONE"
            :key="note.id"
            :note="note"
            :data-id="note.id"
            class="kanban-card-item"
            @open="openNote"
        />

        <div v-show="columns.DONE.length === 0" class="kanban-empty">
          Перетащите задачи сюда
        </div>
      </div>
    </div>

    <NoteDetailsModal
        v-if="activeNote"
        :note="activeNote"
        @close="closeNote"
        @changed="reload"
    />
  </div>
</template>

<script>
import Sortable from "sortablejs";
import { getNotes, updateNoteStatus } from "@/api";
import NoteCard from "./NoteCard.vue";
import NoteDetailsModal from "./NoteDetailsModal.vue";

export default {
  name: "NotesKanban",
  components: { NoteCard, NoteDetailsModal },

  props: {
    userId: { type: String, required: true },
    type: { type: String, default: "" },
    refreshKey: { type: Number, default: 0 }
  },

  data() {
    return {
      notes: [],
      activeNote: null,
      sortableInstances: []
    };
  },

  computed: {
    columns() {
      return {
        TODO: this.notes.filter(n => !n.status || n.status === "TODO"),
        IN_PROGRESS: this.notes.filter(n => n.status === "IN_PROGRESS"),
        DONE: this.notes.filter(n => n.status === "DONE")
      };
    }
  },

  watch: {
    userId: "reload",
    type: "reload",
    refreshKey: "reload"
  },

  async mounted() {
    await this.reload();

    this.$nextTick(() => {
      this.initDragAndDrop();
    });
  },

  beforeUnmount() {
    this.destroySortable();
  },

  methods: {
    async reload() {
      if (!this.userId) return;
      this.notes = await getNotes(this.userId, this.type);
    },

    destroySortable() {
      this.sortableInstances.forEach(s => s?.destroy?.());
      this.sortableInstances = [];
    },

    initDragAndDrop() {
      const columns = [
        this.$refs.todoColumn,
        this.$refs.progressColumn,
        this.$refs.doneColumn
      ];

      if (!columns.every(Boolean)) return;

      columns.forEach(column => {
        const sortable = new Sortable(column, {
          group: "kanban",
          animation: 200,

          draggable: ".kanban-card-item",

          ghostClass: "note-ghost",
          chosenClass: "note-chosen",
          dragClass: "note-drag",

          filter: ".kanban-empty",
          preventOnFilter: false,

          onEnd: async (evt) => {
            const noteId = evt.item?.dataset?.id;
            const newStatus = evt.to?.dataset?.status;
            if (!noteId || !newStatus) return;

            const note = this.notes.find(n => n.id === noteId);
            if (!note) return;

            const oldStatus = note.status || "TODO";
            if (oldStatus === newStatus) return;

            // 🔑 только меняем данные — DOM не трогаем
            note.status = newStatus;

            try {
              await updateNoteStatus(noteId, newStatus);
            } catch {
              note.status = oldStatus;
            }
          }
        });

        this.sortableInstances.push(sortable);
      });
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
.kanban {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
}

.kanban-column {
  background: #fff;
  border-radius: 16px;
  border: 1px solid var(--ui-border);
  display: flex;
  flex-direction: column;
  min-height: 500px;
}

.kanban-header {
  padding: 16px 20px;
  border-bottom: 1px solid var(--ui-border);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.kanban-title {
  font-size: 15px;
  font-weight: 700;
}

.kanban-count {
  background: var(--ui-primary);
  color: #fff;
  font-size: 12px;
  padding: 4px 10px;
  border-radius: 12px;
}

.kanban-cards {
  padding: 16px;
  display: flex;
  flex-direction: column;
  gap: 12px;
  flex: 1;
  overflow-y: auto;
}

.kanban-empty {
  text-align: center;
  padding: 40px 20px;
  color: var(--ui-text-muted);
  border: 2px dashed var(--ui-border);
  border-radius: 12px;
  pointer-events: none;
}

:deep(.note-ghost) {
  opacity: 0.4;
}

:deep(.kanban-card-item) {
  cursor: grab;
}

@media (max-width: 1024px) {
  .kanban {
    grid-template-columns: 1fr;
  }
}
</style>
