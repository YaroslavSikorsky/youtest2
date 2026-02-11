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
/* ПАНЕЛЬ */
.system-drawer {
  position: fixed;
  top: 10%;
  left: 0;
  width: 220px;
  height: 80vh;
  background: var(--ui-surface);       /* фон как у карточек */
  border-radius: 0 var(--ui-radius-lg) var(--ui-radius-lg) 0;
  padding: 24px 16px;
  display: flex;
  flex-direction: column;
  gap: 12px;
  z-index: 2100;
  box-shadow: var(--shadow-md);       /* мягкая тень */
  font-family: var(--ui-font);        /* шрифт UI-kit */
}

/* ПУНКТЫ (заменяем на btn) */
.system-item {
  color: var(--ui-text);
  text-decoration: none;
  padding: 10px 14px;
  border-radius: var(--ui-radius-md);
  background: var(--ui-bg);           /* светлый фон */
  border: 1px solid var(--ui-border);
  font-weight: 500;
  text-align: left;
  transition: all 0.2s;
}

.system-item:hover {
  background: var(--ui-primary);
  color: #fff;
  border-color: var(--ui-primary);
}

/* Кнопка Выйти */
.logout {
  margin-top: auto;
  background: var(--ui-primary-600);
  color: #fff;
  border: none;
}

.logout:hover {
  background: var(--ui-primary);
}

/* КНОПКА-ХЕНДЛ */
.system-handle {
  position: fixed;
  left: 6px;
  top: 50%;
  transform: translateY(-50%);
  width: 14px;
  height: 80vh;
  background: var(--ui-primary-600);
  border-radius: var(--ui-radius-md);
  cursor: pointer;
  z-index: 2000;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0;
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
  background: rgba(0,0,0,0.2);
  z-index: 2050;
}
</style>
