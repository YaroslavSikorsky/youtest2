<template>
  <div class="landing">
    <!-- HERO -->
    <section class="landing-hero">
      <div class="hero-left">
        <h1 class="hero-title">
          Фокус.<br/>
          Минимализм.<br/>
          Пространство для ясности.
        </h1>

        <p class="hero-subtitle">
          Здесь нет шума. Только мысли, которые становятся глубже,
          когда ничто не мешает им проявиться
        </p>

        <div class="hero-buttons">
          <router-link to="/notes" class="btn btn--primary">
            Начать бесплатно
          </router-link>
          <button class="btn btn--ghost">Скачать</button>
        </div>
      </div>

      <div class="hero-preview">
        Превью
      </div>
    </section>

    <!-- DARK CTA -->
    <section class="cta-dark">
      <div class="cta-dark__inner">
        <div class="cta-logo">U</div>

        <p class="cta-text">
          только ты и ничего лишнего
        </p>

        <button class="btn btn--primary">
          попробовать
        </button>
      </div>
    </section>

    <!-- FEATURES -->
    <section class="features">
      <div class="feature">
        <h3>Помнить о важном</h3>
        <p>
          Не только задачи.<br />
          Дни рождения близких, обещания самому себе, мысли,
          к которым хочется вернуться позже.
        </p>
      </div>

      <div class="feature">
        <h3>Быстро находить</h3>
        <p>
          Ничего не прячется.<br />
          Мысли всегда на своих местах — когда они нужны,
          а не когда ты готов их искать.
        </p>
      </div>

      <div class="feature">
        <h3>Упрощать и помогать</h3>
        <p>
          Пространство подстраивается под тебя.<br />
          Форма не мешает содержанию,
          а интерфейс остаётся в тени.
        </p>
      </div>

      <div class="feature">
        <h3>Планировать и действовать</h3>
        <p>
          Без спешки и давления.<br />
          Мысли выстраиваются в порядок,
          который помогает двигаться дальше.
        </p>
      </div>
    </section>

    <!-- SLOGAN -->
    <section class="slogan">
      <h2>
        Управляй информацией из каждого<br/>
        аспекта своей жизни
      </h2>
      <p>где угодно и когда угодно</p>
    </section>

    <!-- FOOTER -->
    <footer class="landing-footer">
      <div class="footer-icons">
        <span></span>
        <span></span>
        <span></span>
      </div>

      <div class="footer-links">
        <a href="#">Политика конфиденциальности</a>
        <a href="#">Пользовательское соглашение</a>
      </div>
    </footer>
  </div>
</template>


<script>
export default {
  name: "HomePage",
};
</script>

<style scoped>
/* =========================
   HERO — пространство, не коробка
========================= */
.landing-hero {
  min-height: calc(100vh - 68px);
  margin-top: 68px;

  display: flex;
  align-items: center;
  justify-content: space-between;

  padding: 0 80px;
  gap: 120px;
}

.hero-left {
  max-width: 520px;
}

.hero-title {
  font-size: 48px;
  line-height: 1.05;
}

.hero-subtitle {
  font-size: 16px;
  line-height: 1.6;
  color: var(--ui-text-muted);
  margin-top: 24px;
}

.hero-buttons {
  margin-top: 40px;
  display: flex;
  gap: 16px;
}

.hero-preview {
  width: 600px;
  height: 380px;
  border-radius: var(--ui-radius-lg);
  background: linear-gradient(135deg, #97a7ff 0%, #e2a7ff 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  color: #fff;
}

/* =========================
   DARK CTA
========================= */
.cta-dark {
  background: #0b0c10;
  color: #fff;
  padding: 160px 20px;
}

.cta-dark__inner {
  max-width: 760px;
  margin: 0 auto;
  text-align: center;
}

.cta-logo {
  font-size: 52px;
  font-weight: 800;
  letter-spacing: 2px;
  margin-bottom: 24px;
}

.cta-text {
  font-size: 14px;
  opacity: 0.75;
  margin-bottom: 32px;
}

/* =========================
   FEATURES — спокойно и широко
========================= */
.features {
  max-width: 1100px;
  margin: 160px auto 140px;
  padding: 0 80px;

  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 96px 120px;
}

.feature h3 {
  font-size: 17px;
  font-weight: 700;
  margin-bottom: 14px;
}

.feature p {
  font-size: 16px;
  line-height: 1.7;
  color: var(--ui-text-muted);
  max-width: 440px;
}

/* =========================
   SLOGAN
========================= */
.slogan {
  text-align: center;
  padding: 140px 20px 160px;
}

.slogan h2 {
  font-size: 32px;
  font-weight: 800;
  line-height: 1.25;
  margin-bottom: 12px;
}

.slogan p {
  font-size: 14px;
  color: var(--ui-text-muted);
}

/* =========================
   FOOTER
========================= */
.landing-footer {
  padding: 120px 20px 80px;
  text-align: center;
}

.footer-icons {
  display: flex;
  justify-content: center;
  gap: 48px;
  margin-bottom: 48px;
}

.footer-icons span {
  width: 48px;
  height: 48px;
  background: var(--ui-primary);
  opacity: 0.85;
  border-radius: 50%;
}

.footer-links {
  display: flex;
  justify-content: center;
  gap: 32px;
}

.footer-links a {
  font-size: 12px;
  color: var(--ui-text-muted);
  text-decoration: none;
}

/* =========================
   MOBILE
========================= */
@media (max-width: 900px) {
  .landing-hero {
    flex-direction: column;
    padding: 40px 20px;
    gap: 40px;
  }

  .hero-preview {
    width: 100%;
    height: 220px;
  }

  .features {
    grid-template-columns: 1fr;
    padding: 0 20px;
    gap: 56px;
  }
}
</style>