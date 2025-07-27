
<template>
  <div class="note-form">
    <form @submit.prevent="submitNote" class="form-row">
      <input v-model="newNoteText" type="text" placeholder="Введите текст" required />
      <button type="submit" class="button">Добавить запись</button>
    </form>
  </div>
</template>

<script>
export default {
  name: "AddNotes",
  data() {
    return {
      newNoteText: ""
    };
  },
  methods: {
    async submitNote() {
      try {
        const response = await fetch('http://localhost:8087/notes', {
          method: 'POST',
          headers: { 'Content-Type': 'text/plain' },
          body: this.newNoteText
        });
        if (!response.ok) throw new Error("Ошибка при отправке заметки");
        this.newNoteText = "";
        this.$emit('noteAdded');
      } catch (error) {
        console.error("Ошибка при добавлении заметки:", error);
      }
    }
  }
};
</script>