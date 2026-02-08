<template>
  <!-- КНОПКА -->
  <div class="system-handle" @click="open = true">
    ☰
  </div>

  <!-- ПАНЕЛЬ -->
  <transition name="system-slide">
    <aside v-if="open" class="system-drawer">
      <router-link
          v-for="item in menu"
          :key="item.label"
          :to="item.path"
          class="system-item"
          @click="open = false"
      >
        {{ item.label }}
      </router-link>

      <button class="system-item logout" @click="logout">
        Выйти
      </button>
    </aside>
  </transition>

  <div v-if="open" class="system-overlay" @click="open = false" />
</template>

<script>
export default {
  name: "SystemDrawer",
  data() {
    return {
      open: false,
      menu: [
        { label: "Профиль", path: "/profile" },
        { label: "Настройки", path: "/settings" },
        { label: "Подписка", path: "/payment" }
      ]
    };
  },
  methods: {
    logout() {
      localStorage.removeItem("user");
      localStorage.removeItem("auth");
      this.open = false;
      this.$router.push("/login");
    }
  }
};
</script>

<style scoped>
/* КНОПКА */
.system-handle {
  position: fixed;
  left: 6px;
  top: 50%;
  transform: translateY(-50%);
  width: 14px;              /* 🔥 в 3 раза уже */
  height: 80vh;             /* 🔥 80% экрана */
  background: var(--ui-primary);
  border-radius: 10px;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  z-index: 2000;
  font-size: 0;             /* скрываем ☰, оставляем «полоску» */
}


/* ПАНЕЛЬ */
.system-drawer {
  position: fixed;
  top: 10%;
  left: 0;
  width: 220px;
  height: 80vh;
  background: var(--ui-primary);
  border-radius: 0 18px 18px 0;
  padding: 24px 16px;
  display: flex;
  flex-direction: column;
  gap: 12px;
  z-index: 2100;
}

/* ПУНКТЫ */
.system-item {
  color: #fff;
  text-decoration: none;
  padding: 10px 14px;
  border-radius: 12px;
  background: rgba(255,255,255,0.12);
}

.system-item:hover {
  background: rgba(255,255,255,0.25);
}

.logout {
  margin-top: auto;
  background: rgba(0,0,0,0.3);
}

/* АНИМАЦИЯ */
.system-slide-enter-active,
.system-slide-leave-active {
  transition: transform .25s ease;
}

.system-slide-enter-from,
.system-slide-leave-to {
  transform: translateX(-100%);
}

/* ОВЕРЛЕЙ */
.system-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0,0,0,0.35);
  z-index: 2050;
}
</style>
