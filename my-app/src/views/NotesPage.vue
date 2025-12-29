<template>
  <div class="notes-layout">
    <!-- LEFT PANEL -->
    <aside class="sidebar">

      <!-- MICRO USER PROFILE -->
      <router-link
          v-if="user"
          to="/profile"
          class="user-mini-profile"
      >
        <div class="avatar">
          {{ initials }}
        </div>
        <div class="user-info">
          <div class="user-name">
            {{ user.firstName || "Пользователь" }}
            {{ user.lastName || "" }}
          </div>
          <div class="user-email">
            {{ user.email }}
          </div>
        </div>
      </router-link>

      <!-- MULTILINE INPUT AREA -->
      <AddNote
          :userId="currentUserId"
          :noteType="selectedType"
          @added="handleAddedNote"
      />

      <!-- TYPES SECTION -->
      <div class="note-filters">
        <!-- Большая кнопка "Все заметки" -->
        <button
            :class="['ui-type-pill', 'big-type', { 'is-active': selectedType === null }]"
            @click="selectType(null)"
        >
          Все заметки
        </button>

        <!-- Остальные типы -->
        <button
            v-for="t in type"
            :key="t"
            :class="['ui-type-pill', { 'is-active': selectedType === t }]"
            @click="selectType(t)"
        >
          {{ t }}
        </button>
      </div>

      <!-- Toggle list button -->
      <button class="toggle-btn styled-toggle" @click="toggleNotes">
        {{ showNotes ? "Скрыть список справа" : "Показать список справа" }}
      </button>
    </aside>

    <!-- RIGHT CONTENT (НЕ ТРОГАЕМ) -->
    <div class="content">
      <NotesList
          v-if="showNotes"
          ref="notesList"
          :currentType="selectedType"
          :userId="currentUserId"
      />
    </div>

  </div>
</template>

<script>
import {getTypes, addNote as apiAddNote} from "@/api";
import NotesList from "@/components/NotesList.vue";
import AddNote from "@/components/AddNote.vue";

export default {
  name: "NotesPage",
  components: {NotesList, AddNote},

  data() {
    return {
      type: [],
      selectedType: null,
      showNotes: true,
      currentUserId: null,
      user: null
    };
  },

  computed: {
    initials() {
      if (!this.user) return "?";
      const f = this.user.firstName?.[0] || "";
      const l = this.user.lastName?.[0] || "";
      return (f + l).toUpperCase();
    }
  },

  mounted() {
    this.fetchType();

    const user = JSON.parse(localStorage.getItem("user"));
    if (user && user.id) {
      this.currentUserId = user.id;
      this.user = user;
    }
  },

  methods: {
    async fetchType() {
      try {
        this.type = await getTypes();
      } catch (err) {
        this.type = ["GAME", "HOME", "FILMS", "WISHLIST", "PARENT", "APARTMENT"];
      }
    },

    selectType(t) {
      this.selectedType = this.selectedType === t ? null : t;
      this.$nextTick(() => {
        if (this.$refs.notesList) this.$refs.notesList.fetchNotes();
      });
    },

    toggleNotes() {
      this.showNotes = !this.showNotes;
    },

    async handleAddedNote(payload) {
      try {
        await apiAddNote(payload);
        this.selectedType = payload.type;
        this.$refs.notesList?.fetchNotes();
      } catch (err) {
        console.error(err);
      }
    }
  }
};
</script>

<style scoped>
/* Layout */
.notes-layout {
  display: grid;
  grid-template-columns: 30% 70%;
  gap: 48px;
  max-width: 1800px;
  margin: 100px auto;
  padding: 0 40px;
  align-items: start;
}

/* Sidebar */
.sidebar {
  display: flex;
  flex-direction: column;
  gap: 34px;
}

/* MICRO PROFILE (link-safe) */
.user-mini-profile {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 14px 16px;
  background: var(--ui-surface);
  border: 1px solid var(--ui-border);
  border-radius: 14px;
  box-shadow: var(--shadow-sm);

  text-decoration: none;        /* ← убираем подчёркивание */
  color: inherit;               /* ← не синий текст ссылки */
  transition: box-shadow .18s, transform .18s;
}

.user-mini-profile:hover {
  box-shadow: var(--shadow-md);
  transform: translateY(-2px);
}

/* AVATAR */
.avatar {
  width: 44px;
  height: 44px;
  border-radius: 50%;
  background: var(--ui-primary);
  color: #fff;
  font-weight: 700;
  font-size: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* TEXT */
.user-info {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.user-name {
  font-weight: 600;
  font-size: 14px;
  color: var(--ui-text);          /* основной текст */
}

.user-email {
  font-size: 12px;
  color: var(--ui-text-muted);   /* серый из UI-kit */
}


/* Filters */
.note-filters {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px;
}

.big-type {
  grid-column: span 2;
  font-size: 16px;
  padding: 14px;
}

/* Toggle button */
.styled-toggle {
  margin-top: 116px;
  padding: 12px 16px;
  border-radius: 10px;
  border: 1px solid var(--ui-border);
  background: #fff;
  color: #666;
  font-size: 14px;
  transition: 0.2s;
}

.styled-toggle:hover {
  background: #f0f3ff;
  color: var(--ui-primary);
  border-color: var(--ui-primary);
}


@media (max-width: 900px) {
  .notes-layout {
    grid-template-columns: 100%;
    padding: 0 20px;
    margin: 20px auto;
  }
}
.content {
  line-height: 0;
}

.content * {
  line-height: normal;
}

</style>
