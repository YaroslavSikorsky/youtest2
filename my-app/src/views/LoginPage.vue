<template>
  <div class="auth-page">
    <AppHeader />

    <div class="auth-container">
      <div class="auth-card">
        <h2 class="auth-title">Войти</h2>

        <form @submit.prevent="handleLogin" class="auth-form">
          <input v-model="email" type="email" class="input" placeholder="Email" required />
          <input v-model="password" type="password" class="input" placeholder="Пароль" required />
          <button class="btn btn--primary btn--full" type="submit">Войти</button>
        </form>

        <p class="auth-footer">
          Нет аккаунта?
          <router-link to="/register" class="btn btn--ghost">
            Регистрация
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
    return { email: "", password: "" };
  },
  methods: {
    async handleLogin() {
      try {
        const res = await fetch(`${API_URL}/notes/login`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ email: this.email, password: this.password }),
        });

        if (!res.ok) {
          alert(await res.text());
          return;
        }

        const user = await res.json();
        localStorage.setItem("auth", "true");
        localStorage.setItem("user", JSON.stringify(user));
        this.$router.push("/notes");
      } catch (e) {
        console.error(e);
        alert("Ошибка сервера");
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
  max-width: 520px; /* увеличенная ширина */
}

.auth-title {
  text-align: center;
  margin-bottom: 24px;
  font-size: 24px;
}

.auth-form {
  display: flex;
  flex-direction: column;
  gap: 16px;
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
