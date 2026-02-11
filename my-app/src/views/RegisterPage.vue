<template>
  <div class="auth-page">
    <AppHeader />

    <div class="auth-container">
      <div class="auth-card">
        <h2 class="auth-title">Регистрация</h2>

        <form @submit.prevent="handleRegister" class="auth-form">
          <input
              v-model="email"
              type="email"
              class="input"
              placeholder="Почта"
              required
          />
          <input
              v-model="password"
              type="password"
              class="input"
              placeholder="Пароль"
              required
          />

          <div v-if="error" class="auth-error">
            {{ error }}
          </div>

          <button
              class="btn btn--primary btn--full"
              type="submit"
              :disabled="loading"
          >
            {{ loading ? "Регистрация..." : "Зарегистрироваться" }}
          </button>
        </form>

        <p class="auth-footer">
          Уже есть аккаунт?
          <router-link to="/login" class="btn btn--ghost">
            Войти
          </router-link>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import AppHeader from "@/components/AppHeader.vue";
import { API_URL } from "@/api";

export default {
  components: { AppHeader },
  data() {
    return {
      email: "",
      password: "",
      loading: false,
      error: null,
    };
  },
  methods: {
    async handleRegister() {
      this.error = null;
      this.loading = true;

      try {
        const res = await fetch(`${API_URL}/notes/users`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ email: this.email, password: this.password }),
        });

        if (!res.ok) {
          throw new Error("Пользователь с таким email уже существует");
        }

        this.$router.push("/login");
      } catch (e) {
        this.error = e.message || "Ошибка регистрации";
      } finally {
        this.loading = false;
      }
    },
  },
};
</script>

<style scoped>
.auth-page {
  min-height: 100vh;
  background: var(--ui-bg);
  display: flex;
  flex-direction: column;
}

.auth-container {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  padding-top: 140px;
  width: 100%;
}

.auth-card {
  background: var(--ui-surface);
  padding: 32px 28px;
  border-radius: var(--ui-radius-lg);
  box-shadow: var(--shadow-md);
  border: none;
  width: 100%;
  max-width: 520px; /* увеличенная ширина для гармоничного вида */
}

.auth-title {
  text-align: center;
  margin-bottom: 24px;
  font-size: 24px; /* чуть больше заголовка */
}

.auth-form {
  display: flex;
  flex-direction: column;
  gap: 16px; /* увеличен отступ между полями */
}

.auth-error {
  color: #d33;
  font-size: 14px;
  text-align: center;
}

.auth-footer {
  text-align: center;
  margin-top: 16px;
  color: var(--ui-text-muted);
}

.auth-footer .btn {
  margin-left: 8px;
  padding: 8px 12px;
}

/* Адаптивность */
@media (max-width: 600px) {
  .auth-card {
    max-width: 90%;
    padding: 28px 20px;
  }

  .auth-title {
    font-size: 22px;
  }
}
</style>
