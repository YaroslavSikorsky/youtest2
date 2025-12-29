<template>
<!--  <div class="notes-page">-->

    <!-- Правая колонка: список заметок -->
<!--    <div class="notes-right">-->
      <div class="notes-list" aria-live="polite">
        <transition-group name="fade" tag="div">
          <NoteCard
              v-for="(n, index) in notes"
              :key="n.id || index"
              :note="n"
          />
        </transition-group>
      </div>
<!--    </div>-->

<!--  </div>-->
</template>

<script>
import NoteCard from "./NoteCard.vue";

export default {
  name: "NotesList",
  components: { NoteCard  },

  props: {
    currentType: String,
    userId: String
  },

  data() {
    return {
      notes: [],
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

  watch: {
    currentType() {
      this.fetchNotes();
    },
    userId() {
      this.fetchNotes();
    }
  },

  mounted() {
    this.fetchNotes();

    const savedUser = localStorage.getItem("user");
    if (savedUser) {
      this.user = JSON.parse(savedUser);
    }
  },

  methods: {
    async fetchNotes() {
      if (!this.userId) return;
      try {
        const url = `http://localhost:8087/notes?userId=${this.userId}&type=${this.currentType || ""}`;
        const res = await fetch(url);
        if (!res.ok) throw new Error("Ошибка при получении заметок");
        this.notes = await res.json();
      } catch (err) {
        console.error("fetchNotes error", err);
      }
    }
  }
};
</script>




<style scoped>

.notes-list {
  margin: 0;
  padding: 0;

  /* 🔥 убираем whitespace text-node */
  font-size: 0;

  /* защита от line-height */
  line-height: 0;
}

/* возвращаем нормальный текст детям */
.notes-list * {
  font-size: initial;
  line-height: initial;
}


</style>
