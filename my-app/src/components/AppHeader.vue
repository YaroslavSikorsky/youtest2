<template>
  <header class="app-header">
    <div class="app-header__inner">
      <!-- Левая часть — бренд -->
      <router-link to="/" class="brand-text">
        <span class="brand-thin">YOU</span>
      </router-link>

      <!-- Центр — логотип -->
      <router-link to="/" class="brand-center">
        <img src="@/assets/1111.png" alt="logo"/>
      </router-link>

      <!-- Правая часть — навигация -->
      <nav class="nav">
        <!-- Dropdown для пользователя -->
        <div v-if="user" class="user-dropdown" ref="dropdownRef">
          <div class="dropdown-button" @click="toggleDropdown">
            {{ currentSection }}
            <span class="arrow" :class="{ open: dropdownOpen }">▼</span>
          </div>

          <transition name="fade">
            <div v-if="dropdownOpen" class="dropdown-menu">
              <router-link to="/profile" class="dropdown-item" @click.native="selectSection('Профиль')">
                Профиль
              </router-link>
              <router-link v-for="item in menuItems"
                           :key="item.label"
                           :to="item.path"
                           class="dropdown-item"
                           @click.native="selectSection(item.label)">
                {{ item.label }}
              </router-link>
              <button class="dropdown-item btn-logout" @click="logout">Выйти</button>
            </div>
          </transition>
        </div>

        <!-- Если пользователь не залогинен — кнопка Войти -->
        <router-link v-else to="/login" class="btn btn--primary">Войти</router-link>
      </nav>
    </div>
  </header>
</template>

<script>
export default {
  name: "AppHeader",
  data() {
    return {
      user: null,
      dropdownOpen: false,
      currentSection: "Заметки",
      menuItems: [
        { label: "Заметки", path: "/notes" },
        { label: "Настройки", path: "/settings" },
        { label: "Подписка", path: "/payment" }
      ]
    };
  },
  mounted() {
    const savedUser = localStorage.getItem("user");
    if (savedUser) this.user = JSON.parse(savedUser);

    document.addEventListener("click", this.handleClickOutside);
  },
  beforeUnmount() {
    document.removeEventListener("click", this.handleClickOutside);
  },
  methods: {
    toggleDropdown() {
      this.dropdownOpen = !this.dropdownOpen;
    },
    handleClickOutside(e) {
      const dropdown = this.$refs.dropdownRef;
      if (dropdown && !dropdown.contains(e.target)) {
        this.dropdownOpen = false;
      }
    },
    selectSection(label) {
      this.currentSection = label;
      this.dropdownOpen = false;
    },
    logout() {
      localStorage.removeItem("user");
      localStorage.removeItem("auth");
      this.user = null;
      this.dropdownOpen = false;
      this.$router.push("/login");
    }
  }
};
</script>

<style scoped>
.app-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 62px;
  background: var(--ui-surface);
  border-bottom: 1px solid var(--ui-border);
  backdrop-filter: blur(8px);
  z-index: 1100;
}

.app-header__inner {
  max-width: 1700px;
  margin: 0 auto;
  height: 100%;
  display: grid;
  grid-template-columns: 1fr auto 1fr;
  align-items: center;
  padding: 0 20px;
}

.brand-text, .brand-center {
  display: flex;
  align-items: center;
  text-decoration: none;
}

.brand-thin {
  font-weight: 300;
  font-size: 32px;
  letter-spacing: 4px;
  color: var(--ui-text);
  text-transform: uppercase;
}

.brand-center img {
  height: 40px;
}

.nav {
  display: flex;
  align-items: center;
  gap: 12px;
  justify-self: end;
}

.user-dropdown {
  position: relative;
}

.dropdown-button {
  padding: 8px 14px;
  border-radius: 12px;
  background-color: var(--ui-primary);
  color: #fff;
  font-weight: 500;
  font-size: 14px;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 6px;
}

.arrow {
  transition: transform 0.2s;
}

.arrow.open {
  transform: rotate(180deg);
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  right: 0;
  background: var(--ui-surface);
  border: 1px solid var(--ui-border);
  border-radius: 12px;
  box-shadow: var(--shadow-md);
  display: flex;
  flex-direction: column;
  min-width: 160px;
  z-index: 2000;
  margin-top: 6px;
  overflow: hidden;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.2s;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}

.dropdown-item {
  padding: 10px 16px;
  font-size: 14px;
  color: var(--ui-text);
  text-decoration: none;
  transition: background 0.15s;
}

.dropdown-item:hover {
  background: var(--ui-primary);
  color: #fff;
}

.btn-logout {
  background: none;
  border: none;
  text-align: left;
  cursor: pointer;
  font-weight: 600;
  color: #e74c3c;
  padding: 10px 16px;
}

.btn-logout:hover {
  background: #fce8e6;
  color: #c0392b;
}
</style>
