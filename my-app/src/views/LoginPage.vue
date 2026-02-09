<template>
  <div style="min-height:100vh;background:var(--ui-bg)">
    <AppHeader/>

    <div class="container" style="max-width:480px;margin-top:140px">
      <div class="note-create-card">
        <h2 style="text-align:center;margin-bottom:18px">Войти</h2>

        <form @submit.prevent="handleLogin" style="display:flex;flex-direction:column;gap:12px">
          <input v-model="email" type="email" class="input" placeholder="Email" required/>
          <input v-model="password" type="password" class="input" placeholder="Пароль" required/>
          <button class="btn btn--primary btn--full" type="submit">Войти</button>
        </form>

        <p style="text-align:center;margin-top:12px;color:var(--ui-text-muted)">
          Нет аккаунта?
          <router-link to="/register" class="btn btn--ghost" style="margin-left:8px;padding:6px 10px">
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
  data() { return { email: "", password: "" }; },
  methods: {
    async handleLogin() {
      try {
        const res = await fetch(`${API_URL}/notes/login`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ email: this.email, password: this.password })
        });
        if (!res.ok) { alert(await res.text()); return; }
        const user = await res.json();
        localStorage.setItem("auth", "true");
        localStorage.setItem("user", JSON.stringify(user));
        this.$router.push("/profile");
      } catch (e) { console.error(e); alert("Ошибка сервера"); }
    }
  }
};
</script>
