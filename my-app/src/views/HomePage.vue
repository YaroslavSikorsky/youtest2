<template>
  <div class="landing">
    <!-- HERO -->
    <section class="landing-hero">
      <div class="hero-left">
        <h1 class="hero-title">
          Фокус. <br/>
          Минимализм. <br/>
          Пространство <br/>
          для ясности.
        </h1>

        <p class="hero-subtitle">
          Здесь нет шума. Только мысли, которые становятся глубже, когда ничто не мешает им проявиться.
        </p>

        <div class="hero-buttons">
          <!-- Используем кнопки ui-kit -->
          <router-link to="/notes" class="btn btn--primary">
            Начать бесплатно
          </router-link>
          <button class="btn btn--ghost">Скачать</button>
        </div>
      </div>

      <div class="hero-preview">
        Видео-превью (скоро)
      </div>
    </section>

    <!-- FEATURES -->
    <section
        class="features"
        style="max-width:1100px; margin:40px auto 120px; padding:0 20px; display:grid; grid-template-columns:repeat(3,1fr); gap:40px;"
    >
      <div>
        <h3 style="font-size:20px; font-weight:700; margin-bottom:10px;">Чистота мысли</h3>
        <p style="color:var(--ui-text-muted); line-height:1.55;">
          Когда исчезают лишние элементы, остаётся только суть. Мысли становятся точнее, глубже и честнее.
        </p>
      </div>

      <div>
        <h3 style="font-size:20px; font-weight:700; margin-bottom:10px;">Тишина внутри</h3>
        <p style="color:var(--ui-text-muted); line-height:1.55;">
          Интерфейс не отвлекает — он растворяется. В этой тихой среде слышнее то, что происходит внутри.
        </p>
      </div>

      <div>
        <h3 style="font-size:20px; font-weight:700; margin-bottom:10px;">Свобода фокуса</h3>
        <p style="color:var(--ui-text-muted); line-height:1.55;">
          Минимум действий, максимум присутствия. Вы управляете своим вниманием — а не интерфейс вами.
        </p>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: "HomePage",
};
</script>

<style scoped>
/* Тут используется уже существующий ui-kit из CSS */
</style>
