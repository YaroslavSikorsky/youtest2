<template>
  <div id="NotesPage">
    <header class="site-header">
      <span class="site-title">Y O U</span>
      <div class="header-buttons">
        <button class="button">Вход</button>
        <button class="button">Регистрация</button>
      </div>
    </header>

    <main class="content">
      <img :src="logo" alt="Логотип" class="logo" />

      <AddNotes @noteAdded="refreshNotes" />

      <button class="button full-width" @click="toggleNotes">
        {{ showNotes ? 'Скрыть список' : 'Список заметок' }}
      </button>

      <NotesList ref="notesList" v-if="showNotes" />
    </main>
  </div>
</template>

<script>
import AddNotes from '../components/AddNotes.vue';
import NotesList from '../components/NotesList.vue';
import LogoImage from '../assets/1111.png';

export default {
  name: 'NotesPage',
  components: {
    AddNotes,
    NotesList
  },
  data() {
    return {
      logo: LogoImage,
      showNotes: false
    };
  },
  methods: {
    refreshNotes() {
      if (this.showNotes) {
        this.$refs.notesList.fetchNotes();
      }
    },
    toggleNotes() {
      this.showNotes = !this.showNotes;
      if (this.showNotes) {
        this.$nextTick(() => {
          this.$refs.notesList.fetchNotes();
        });
      }
    }
  }
};

</script>

<style>
html, body {
  height: 100%;
  margin: 0;
  padding: 0;
}
body {
  background-color: #f5f6f7;
  /*background: linear-gradient(to bottom, #ffffff, #f2f8f1);*/
  margin: 0;
  padding: 0;
  font-family: 'Arial', sans-serif;
}

#app {
  text-align: center;
}

.site-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 54px;
  background-color: #fff;
  border-bottom: 1px solid #ddd;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 30px;
  z-index: 1000;

}

.site-title {
  font-weight: bold;
  font-size: 18px;
  color: #333;
}

.header-buttons {
  display: flex;
  gap: 8px;
}

.content {
  max-width: 600px;
  margin: 120px auto 40px;
  padding: 0 20px;
}

.logo {
  width: 180px;
  margin-bottom: 30px;
}

.button {
  padding: 10px 18px;
  background-color: #0073e2;
  border: 1px solid #ccc;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
  font-weight: bold;
  transition: background-color 0.3s, transform 0.2s;
  color: white;
}

/*.button:hover {*/
/*  background-color: #677d64;*/
/*}*/

.button:active {
  transform: scale(1.05);
}

.full-width {
  width: 488px;
  margin-top: 20px;
}
input[type="text"] {
  padding: 10px;
  font-size: 14px;
  border-radius: 8px;
  border: 1px solid #ccc;
  width: 50%;
}
input[type="text"]:hover {
  border-color: #92b18e;
  box-shadow: 0 0 4px rgba(146, 177, 142, 0.4);
}
input[type="text"]:focus {
  outline: none;
}
.form-row {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
}
.notes-list {
  list-style: none; /* Убирает точки */
  padding: 0;
  margin: 0;
}

</style>