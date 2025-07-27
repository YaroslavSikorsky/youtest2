<template>
  <div>
    <transition-group name="fade" tag="ul" class="notes-list">
      <li v-for="(note, index) in notes" :key="index" class="note-item">
        {{ note }}
      </li>
    </transition-group>
  </div>
</template>

<script>
export default {
  name: "NotesList",
  data() {
    return {
      notes: []
    };
  },
  mounted() {
    this.fetchNotes();
  },
  methods: {
    async fetchNotes() {
      try {
        const response = await fetch('http://localhost:8087/notes');
        if (!response.ok) throw new Error("Ошибка при загрузке заметок");
        this.notes = await response.json();
      } catch (error) {
        console.error("Ошибка при получении заметок:", error);
      }
    }
  }
};

</script>

<style scoped>
.notes-list {
  list-style: none;
  padding: 0;
  margin: 20px auto;
  max-width: 500px;
  text-align: left;
}

.note-item {
  background: #f6f9f6;
  border: 1px solid #cddccc;
  border-left: 5px solid #92b18e;
  border-radius: 8px;
  padding: 16px 20px;
  margin-bottom: 12px;
  font-size: 15px;
  font-family: 'Arial', sans-serif;
  color: #333;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s ease, background-color 0.3s ease;
  position: relative;
}

.note-item:hover {
  background-color: #eef6ee;
  transform: translateY(-2px);
}

.note-item::before {

  position: absolute;
  left: -28px;
  font-size: 18px;
  top: 50%;
  transform: translateY(-50%);
}
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.6s ease, transform 0.6s ease;
}

.fade-enter-from {
  opacity: 0;
  transform: translateY(-40px); /* элемент появляется сверху */
}
.fade-enter-to {
  opacity: 1;
  transform: translateY(0);
}

.fade-leave-from {
  opacity: 1;
  transform: translateY(0);
}
.fade-leave-to {
  opacity: 0;
  transform: translateY(40px); /* элемент уходит вниз */
}

</style>