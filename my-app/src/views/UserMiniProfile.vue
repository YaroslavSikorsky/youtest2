<template>
  <div class="user-mini-profile">
    <!-- Если пользователь залогинен -->
    <div v-if="user" class="logged-in" @mouseenter="openDropdown" @mouseleave="closeDropdown">
      <div class="avatar">{{ initials }}</div>
      <div class="user-name">{{ user.firstName || "Пользователь" }}</div>

      <div class="dropdown-menu" v-show="dropdownOpen">
        <router-link to="/profile" class="dropdown-item">Профиль</router-link>
        <router-link to="/settings" class="dropdown-item">Настройки</router-link>
        <router-link to="/settings" class="dropdown-item">Группа</router-link>
        <button class="dropdown-item btn-logout" @click="logout">Выйти</button>
      </div>
    </div>

    <!-- Если пользователь не залогинен -->
    <div v-else>
      <router-link to="/login" class="btn btn--primary">Войти</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserMiniProfile",
  data() {
    return {
      user: null,
      dropdownOpen: false
    };
  },
  computed: {
    initials() {
      if (!this.user) return "?";
      const f = this.user.firstName?.[0] || "";
      const l = this.user.lastName?.[0] || "";
      return (f + l).toUpperCase();
    }
  },
  mounted() {
    const savedUser = localStorage.getItem("user");
    if (savedUser) {
      this.user = JSON.parse(savedUser);
    }
  },
  methods: {
    logout() {
      localStorage.removeItem("user");
      localStorage.removeItem("auth");
      this.user = null;
      this.$router.push("/login");
    },
    openDropdown() {
      this.dropdownOpen = true;
    },
    closeDropdown() {
      this.dropdownOpen = false;
    }
  }
};
</script>

<style scoped>
.user-mini-profile {
  position: relative;
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 500;
  cursor: pointer;
}

/* Аватар */
.avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: var(--ui-primary);
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
}

/* Dropdown */
.dropdown-menu {
  position: absolute;
  top: 42px;
  right: 0;
  background: var(--ui-surface);
  border: 1px solid var(--ui-border);
  border-radius: 12px;
  box-shadow: var(--shadow-md);
  display: flex;
  flex-direction: column;
  width: 160px;
  z-index: 1200;
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

/* Красная кнопка выхода */
.btn-logout {
  background: none;
  border: none;
  text-align: left;
  cursor: pointer;
  font-weight: 600;
  color: #e74c3c;
  padding: 10px 16px;
  transition: 0.15s;
}

.btn-logout:hover {
  background: #fce8e6;
  color: #c0392b;
}
</style>
