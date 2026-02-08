<template>
  <div class="note-create-card">
    <form @submit.prevent="submit">
      <input
          v-model="title"
          type="text"
          class="input note-title-input"
          placeholder="Введите название..."
      />
      <textarea
          v-model="text"
          class="note-textarea"
          placeholder="Введите заметку..."
      ></textarea>
      <button class="btn btn--primary" type="submit">Добавить</button>
    </form>
  </div>
</template>

<script>
// import { addNote  } from "@/api";

export default {
  name: "AddNote",
  props: {
    userId: { type: String, required: true },
    noteType: { type: String, required: false },
  },
  data() {
    return {
      title: "",
      text: "",
    };
  },
  methods: {
    async submit() {
      if (!this.title.trim()) {
        alert("Введите заголовок заметки");
        return;
      }
      if (!this.text.trim()) {
        alert("Введите текст заметки");
        return;
      }
      if (!this.userId) {
        alert("Пользователь не найден");
        return;
      }
      if (!this.noteType) {
        alert("Выберите тип заметки");
        return;
      }

      const payload = {
        title: this.title.trim(),
        text: this.text.trim(),
        type: this.noteType,
        userId: this.userId,
      };

      this.$emit("added", payload);
      this.title = "";
      this.text = "";
    },
  },
};
</script>

<style scoped>
.note-create-card {}
.note-title-input {
  width: 100%;
  margin-bottom: 8px;
}
.note-textarea {
  width: 100%;
  min-height: 90px;
  padding: 12px;
  border-radius: 10px;
  border: 1px solid var(--ui-border);
  color: var(--ui-primary);

}


</style>



