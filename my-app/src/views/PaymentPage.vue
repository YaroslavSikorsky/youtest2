<template>
  <div class="payment-page">
    <div class="payment-container">

      <!-- CARD -->
      <div class="payment-card">

        <!-- HEADER -->
        <div class="payment-header">
          <h1 class="payment-title">Подписка YOU</h1>
          <p class="payment-subtitle">
            Минимум шума. Максимум ясности.
          </p>
        </div>

        <!-- PLAN -->
        <div class="payment-plan">
          <div class="plan-info">
            <div class="plan-name">Премиум</div>
            <div class="plan-desc">
              Без ограничений. Все функции. Полный фокус.
            </div>
          </div>

          <div class="plan-price">
            <span class="price">299 ₽</span>
            <span class="period">/ месяц</span>
          </div>
        </div>

        <!-- FEATURES -->
        <ul class="plan-features">
          <li>Неограниченные заметки</li>
          <li>Все типы и фильтры</li>
          <li>Без рекламы</li>
          <li>Приоритетные обновления</li>
        </ul>

        <!-- ACTIONS -->
        <div class="payment-actions">
          <button class="btn btn--primary btn--full">
            Оплатить
          </button>

          <router-link to="/" class="btn btn--ghost btn--full">
            Вернуться назад
          </router-link>
        </div>

        <!-- FOOTNOTE -->
        <p class="payment-footnote">
          Оплата безопасна. Отменить подписку можно в любой момент.
        </p>

      </div>

    </div>
  </div>
</template>

<script>
export default {
  name: "PaymentPage"
};
</script>

<style scoped>
/* PAGE */
.payment-page {
  min-height: 100vh;
  background: var(--ui-bg);
  display: flex;
  justify-content: center;
}

/* CONTAINER */
.payment-container {
  width: 100%;
  max-width: 520px;
  margin: 120px auto 80px;
  padding: 0 20px;
}

/* CARD */
.payment-card {
  background: var(--ui-surface);
  border: 1px solid var(--ui-border);
  border-radius: var(--ui-radius-lg);
  padding: 36px;
  box-shadow: var(--shadow-md);
  display: flex;
  flex-direction: column;
  gap: 28px;
}

/* HEADER */
.payment-header {
  text-align: center;
}

.payment-title {
  margin: 0;
  font-size: 28px;
  font-weight: 800;
}

.payment-subtitle {
  margin-top: 8px;
  font-size: 14px;
  color: var(--ui-text-muted);
}

/* PLAN */
.payment-plan {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  border-radius: var(--ui-radius-md);
  background: #f6f7ff;
  border: 1px solid var(--ui-border);
}

.plan-name {
  font-weight: 700;
  font-size: 16px;
}

.plan-desc {
  font-size: 13px;
  color: var(--ui-text-muted);
  margin-top: 4px;
}

.plan-price {
  text-align: right;
}

.price {
  font-size: 22px;
  font-weight: 800;
}

.period {
  font-size: 13px;
  color: var(--ui-text-muted);
}

/* FEATURES */
.plan-features {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.plan-features li {
  font-size: 14px;
  color: var(--ui-text);
  position: relative;
  padding-left: 18px;
}

.plan-features li::before {
  content: "•";
  position: absolute;
  left: 0;
  color: var(--ui-primary);
  font-weight: 700;
}

/* ACTIONS */
.payment-actions {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

/* FOOTNOTE */
.payment-footnote {
  font-size: 12px;
  color: var(--ui-text-muted);
  text-align: center;
}
</style>
