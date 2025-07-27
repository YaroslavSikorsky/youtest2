<template>
  <div class="landing-page">
    <nav class="top-nav">
      <ul class="nav-menu">
        <li>Текст</li>
        <li>Текст</li>
        <li>Текст</li>
      </ul>
      <button class="login-button">Вход</button>
    </nav>

    <section class="hero-section">
      <div class="hero-text">
        <div class="hero-title">U</div>
        <h2>Только ты и ничего лишнего</h2>
        <div class="hero-buttons">
          <button class="btn primary">Скачать</button>
          <router-link to="/notes" class="btn secondary">Начать бесплатно</router-link>
<!--          <button class="btn secondary">Начать бесплатно</button>-->
        </div>
        <p class="subtext">
          Где угодно, <span class="highlight">когда угодно</span>
        </p>
      </div>

      <div class="hero-media">
        Видео (заглушка)
      </div>
    </section>
  </div>
</template>


<script>
export default {
  name: 'HomePage'
};
</script>

<style scoped>


.landing-page {
  background-color: #f5f6f7;
  font-family: 'Arial', sans-serif;
  padding: 0;
  margin: 0;
}

/* Верхнее меню */
.top-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #ffffff;
  padding: 8px 20px;
  border-bottom: 1px solid #ddd;
}

.nav-menu {
  display: flex;
  gap: 40px;
  list-style: none; /* УБИРАЕМ ТОЧКИ */
  margin: 0;
  padding: 0;
  font-weight: bold;
  font-size: 14px;
  color: #111;
}

.nav-menu li {
  list-style: none;
}

.login-button {
  padding: 10px 18px;
  background-color: #0073e2;
  border: 1px solid #ccc;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
  font-weight: bold;
  color: white;
  transition: background-color 0.3s, transform 0.2s;
}

.login-button:active {
  transform: scale(1.05);
}

/* Контент */
.hero-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 60px 40px;
  max-width: 1200px;
  margin: 0 auto;
  gap: 40px;
  background-color: #f5f6f7;
  height: 70vh;
}

.hero-text {
  max-width: 500px;
  text-align: left;
}

.hero-title {
  font-size: 64px;
  font-weight: bold;
  margin-bottom: 10px;
  color: #111;
}

.hero-text h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 30px;
  color: #222;
}

.hero-buttons {
  display: flex;
  gap: 15px;
  margin-bottom: 16px;
}

/* КНОПКИ */

/* Стиль основной кнопки (как на NotesPage.vue) */
.btn.primary {
  padding: 10px 18px;
  background-color: #0073e2;
  border: 1px solid #ccc;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
  font-weight: bold;
  color: white;
  transition: background-color 0.3s, transform 0.2s;
}

.btn.primary:active {
  transform: scale(1.05);
}

/* ВТОРАЯ кнопка — белая с синей рамкой */
.btn.secondary {
  padding: 10px 18px;
  background-color: white;
  color: #0073e2;
  border: 2px solid #0073e2;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
  font-weight: bold;
  transition: background-color 0.3s, transform 0.2s;
  text-decoration: none;
}

.btn.secondary:active {
  transform: scale(1.05);
}

/* Градиентная надпись */
.subtext {
  font-size: 14px;
  font-weight: bold;
  background: linear-gradient(to right, #506571 10%, #ff00ff 60%, #0073e2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}
/* Заглушка под видео */
.hero-media {
  width: 520px;
  height: 300px;
  background-color: #d0e3ff;
  border: 4px solid #0073e2;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #333;
  font-weight: bold;
  font-size: 16px;
}
</style>