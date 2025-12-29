<template>
  <div class="profile-page">
    <div class="profile-container">

      <!-- PROFILE CARD -->
      <div v-if="user" class="profile-card">

        <!-- HEADER -->
        <div class="profile-header">
          <div class="avatar">
            {{ initials || "?" }}
          </div>

          <div class="profile-main">
            <h2 class="profile-name">
              {{ user.firstName || "Без имени" }}
              {{ user.lastName || "" }}
            </h2>
            <p class="profile-email">{{ user.email }}</p>
          </div>
        </div>

        <!-- INFO -->
        <div class="profile-info">
          <div class="info-row">
            <span class="info-label">ID пользователя</span>
            <span class="info-value muted">{{ user.id }}</span>
          </div>

          <div class="info-row">
            <span class="info-label">Имя</span>
            <span class="info-value">{{ user.firstName || "—" }}</span>
          </div>

          <div class="info-row">
            <span class="info-label">Фамилия</span>
            <span class="info-value">{{ user.lastName || "—" }}</span>
          </div>

          <div class="info-row">
            <span class="info-label">Email</span>
            <span class="info-value">{{ user.email }}</span>
          </div>

          <div class="info-row">
            <span class="info-label">Дата рождения</span>
            <span class="info-value">{{ user.dateBirthday || "—" }}</span>
          </div>

          <div class="info-row">
            <span class="info-label">ID списка заметок</span>
            <span class="info-value">{{ user.idNoteList || "—" }}</span>
          </div>

          <div class="info-row">
            <span class="info-label">Дата создания</span>
            <span class="info-value">{{ user.createdAt || "—" }}</span>
          </div>
        </div>

        <!-- ACTIONS -->
        <div class="profile-actions">
          <button class="btn btn--primary">
            Редактировать
          </button>
        </div>
      </div>

      <!-- LOADING -->
      <div v-else class="profile-loading">
        Загрузка профиля...
      </div>

    </div>
  </div>
</template>

<script>
export default {
  name: "UserProfile",

  data() {
    return {
      user: null
    };
  },

  mounted() {
    const savedUser = localStorage.getItem("user");

    if (!savedUser) {
      this.$router.push("/login");
      return;
    }

    this.user = JSON.parse(savedUser);
  },

  computed: {
    initials() {
      if (!this.user) return "";
      const f = this.user.firstName?.[0] || "";
      const l = this.user.lastName?.[0] || "";
      return (f + l).toUpperCase();
    }
  }
};
</script>

<style>
/* PAGE */
.profile-page {
  min-height: 100vh;
  background: var(--ui-bg);
  display: flex;
  justify-content: center;
}

/* CONTAINER */
.profile-container {
  width: 100%;
  max-width: 900px;
  margin: 120px auto 80px;
  padding: 0 20px;
}

/* CARD */
.profile-card {
  background: var(--ui-surface);
  border: 1px solid var(--ui-border);
  border-radius: var(--ui-radius-lg);
  padding: 32px;
  box-shadow: var(--shadow-sm);
  display: flex;
  flex-direction: column;
  gap: 28px;
}

/* HEADER */
.profile-header {
  display: flex;
  align-items: center;
  gap: 20px;
}

.avatar {
  width: 84px;
  height: 84px;
  border-radius: 50%;
  background: var(--ui-primary);
  color: #fff;
  font-size: 30px;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
}

.profile-name {
  margin: 0;
  font-size: 26px;
  font-weight: 700;
}

.profile-email {
  margin-top: 6px;
  font-size: 14px;
  color: var(--ui-text-muted);
}

/* INFO */
.profile-info {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.info-row {
  display: flex;
  justify-content: space-between;
  gap: 20px;
}

.info-label {
  color: var(--ui-text-muted);
}

.info-value {
  font-weight: 500;
}

.info-value.muted {
  font-size: 13px;
  color: var(--ui-text-muted);
}

/* ACTIONS */
.profile-actions {
  display: flex;
  justify-content: flex-end;
}

/* LOADING */
.profile-loading {
  margin-top: 140px;
  text-align: center;
  color: var(--ui-text-muted);
  font-size: 16px;
}
</style>
