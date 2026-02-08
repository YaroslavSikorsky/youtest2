<template>
  <!-- 🔵 СИСТЕМНАЯ ВЫДВИЖНАЯ ПОЛОСКА (ПОВЕРХ ВСЕГО) -->
  <SystemDrawer />

  <div class="notes-screen">
    <!-- SIDEBAR С ТЕМАМИ -->
    <aside class="notes-sidebar">
      <nav class="notes-menu">
        <router-link to="/profile" class="notes-menu__item notes-menu__item--muted">
          Профиль
        </router-link>

        <router-link to="/notes" class="notes-menu__item notes-menu__item--muted">
          Мои заметки
        </router-link>

        <router-link to="/calendar" class="notes-menu__item">
          Календарь
        </router-link>

        <div class="notes-menu__divider"></div>

        <button
            v-for="t in types"
            :key="t"
            class="notes-menu__item"
            :class="{ 'notes-menu__item--active': selectedType === t }"
            @click="selectedType = t"
        >
          {{ t }}
        </button>
      </nav>
    </aside>

    <!-- MAIN -->
    <main class="notes-main">
      <header class="notes-main__header">
        <h1 class="notes-main__title">
          {{ selectedType || "Заметки" }}
        </h1>

        <!-- ⬇️ НИЧЕГО НЕ ЛОМАЕМ: просто оборачиваем кнопки -->
        <div style="display: flex; gap: 12px">
          <button class="notes-toggle" @click="toggleView">
            {{ viewMode === "list" ? "Канбан" : "Список" }}
          </button>

          <button class="notes-toggle" @click="toggleMode">
            {{ mode === "edit" ? "Показать записи" : "Новая запись" }}
          </button>
        </div>
      </header>

      <!-- ADD -->
      <AddNote
          v-if="mode === 'edit'"
          :user-id="userId"
          :note-type="selectedType"
          @added="onAdded"
      />

      <!-- LIST -->
      <NotesList
          v-if="mode !== 'edit' && viewMode === 'list'"
          :user-id="userId"
          :type="selectedType"
          :refresh-key="refreshKey"
      />

      <!-- KANBAN -->
      <NotesKanban
          v-if="mode !== 'edit' && viewMode === 'kanban'"
          :user-id="userId"
          :type="selectedType"
          :refresh-key="refreshKey"
      />
    </main>
  </div>
</template>

<script>
import { getTypes, addNote } from "@/api";
import AddNote from "@/components/AddNote.vue";
import NotesList from "@/components/NotesList.vue";
import NotesKanban from "@/components/NotesKanban.vue";
import SystemDrawer from "@/components/SystemDrawer.vue";

export default {
  name: "NotesPage",
  components: {
    AddNote,
    NotesList,
    NotesKanban,
    SystemDrawer
  },

  data() {
    return {
      types: [],
      selectedType: null,
      userId: null,
      mode: "edit",
      refreshKey: 0,
      viewMode: localStorage.getItem("notes:viewMode") || "list"
    };
  },

  async mounted() {
    try {
      this.types = await getTypes();
    } catch {
      this.types = [
        "GAME", "HOME", "FILMS", "WISHLIST",
        "PARENT", "APARTMENT", "TODO", "PROJECT", "PLACE"
      ];
    }

    const user = JSON.parse(localStorage.getItem("user"));
    this.userId = user?.id || null;
  },

  methods: {
    toggleMode() {
      this.mode = this.mode === "edit" ? "list" : "edit";
    },

    toggleView() {
      this.viewMode = this.viewMode === "list" ? "kanban" : "list";
      localStorage.setItem("notes:viewMode", this.viewMode);
    },

    async onAdded(payload) {
      await addNote(payload);
      this.mode = "list";
      this.refreshKey++;
    }
  }
};
</script>

<style scoped>
/* 🔥 СТИЛИ ПОЛНОСТЬЮ ТВОИ, НЕ МЕНЯЛ */
.notes-screen {
  display: grid;
  grid-template-columns: 260px minmax(0, 1fr);
  height: calc(100vh - 62px);
  margin-top: 62px;
}

/* SIDEBAR */
.notes-sidebar {
  background: #ffffff;
  border-right: 1px solid var(--ui-border);
  display: flex;
  flex-direction: column;
  padding: 32px 24px;
}

.notes-logo {
  font-size: 32px;
  font-weight: 800;
  color: var(--ui-primary);
  margin-bottom: 32px;
}

.notes-menu {
  display: flex;
  flex-direction: column;
  gap: 8px;
  padding-left: 1cm;
}

.notes-menu__item {
  text-align: left;
  padding: 8px 0;
  border: none;
  background: transparent;
  cursor: pointer;
  font-size: 15px;
  color: var(--ui-primary);
}

.notes-menu__item--muted {
  color: var(--ui-text-muted);
}

.notes-menu__item--active {
  font-weight: 700;
}

.notes-menu__divider {
  height: 1px;
  margin: 16px 0;
  background: var(--ui-border);
}

/* MAIN */
.notes-main {
  padding: 40px 56px;
  background: var(--ui-bg);
}

.notes-main__header {
  margin-bottom: 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.notes-main__title {
  font-size: 24px;
  font-weight: 700;
  color: var(--ui-primary);
}

.notes-toggle {
  border: none;
  background: transparent;
  color: var(--ui-primary);
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  padding: 6px 10px;
  border-radius: 999px;
}

.notes-toggle:hover {
  background: rgba(107, 124, 255, 0.08);
}

/* MOBILE */
@media (max-width: 900px) {
  .notes-screen {
    grid-template-columns: 1fr;
  }

  .notes-sidebar {
    display: none;
  }

  .notes-main {
    padding: 20px;
  }
}
</style>
