<template>
  <div style="min-height:100vh;background:var(--ui-bg)">
    <AppHeader/>

    <div class="container" style="max-width:480px;margin-top:140px">
      <div
          style="
          background:var(--ui-surface);
          border:1px solid var(--ui-border);
          padding:28px;
          border-radius:16px;
          box-shadow:var(--shadow-sm);
          width:100%;
        "
      >
        <h2 style="text-align:center;margin-bottom:18px">
          Регистрация
        </h2>

        <form
            @submit.prevent="handleRegister"
            style="display:flex;flex-direction:column;gap:12px"
        >
          <!-- EMAIL -->
          <input
              v-model="email"
              type="email"
              class="input"
              placeholder="Почта"
              required
          />

          <!-- PASSWORD -->
          <input
              v-model="password"
              type="password"
              class="input"
              placeholder="Пароль"
              required
          />

          <!-- ERROR -->
          <div
              v-if="error"
              style="color:#d33;font-size:14px;text-align:center"
          >
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

        <p style="text-align:center;margin-top:12px;color:var(--ui-text-muted)">
          Уже есть аккаунт?
          <router-link
              to="/login"
              class="btn btn--ghost"
              style="margin-left:8px;padding:6px 10px"
          >
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
  components: {AppHeader},

  data() {
    return {
      email: "",
      password: "",
      loading: false,
      error: null
    };
  },

  methods: {
    async handleRegister() {
      this.error = null;

      this.loading = true;

      try {
        const res = await fetch(`${API_URL}/notes/users`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            email: this.email,
            password: this.password
          })
        });

        if (!res.ok) {
          // если email уже существует → будет 500 или 409
          throw new Error("Пользователь с таким email уже существует");
        }

        // успех → на логин
        this.$router.push("/login");
      } catch (e) {
        this.error = e.message || "Ошибка регистрации";
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>
