<template>
  <div class="settings-page">
    <div class="settings-container">
      <div class="settings-card" v-if="user">

        <!-- HEADER -->
        <div class="settings-header">
          <h2 class="settings-title">Настройки</h2>
          <p class="settings-subtitle">
            Минимум действий, максимум контроля.
          </p>
        </div>

        <!-- SECTION: VISIBLE NOTE TYPES -->
        <div class="settings-section">
          <div class="section-title">Видимые типы заметок</div>
          <div class="types-grid">
            <label v-for="t in allTypes" :key="t" class="type-checkbox">
              <input type="checkbox" v-model="visibleTypes" :value="t" />
              {{ t }}
            </label>
          </div>
        </div>

        <!-- SECTION: VISIBLE MODAL ELEMENTS -->
        <div class="settings-section">
          <div class="section-title">Элементы в модалке заметки</div>
          <div class="types-grid">
            <label v-for="elem in allModalElements" :key="elem.value" class="type-checkbox">
              <input type="checkbox" v-model="visibleElements" :value="elem.value" />
              {{ elem.label }}
            </label>
          </div>
        </div>

        <!-- SECTION: GROUP / FAMILY -->
        <div class="settings-section">
          <div class="section-title">Группа / Семья</div>

          <div class="group-list">
            <div v-for="member in members" :key="member.email" class="group-member">
              {{ member.name }} ({{ member.email }})
            </div>
          </div>

          <div class="group-add">
            <input v-model="inviteEmail" placeholder="Email нового участника" class="input"/>
            <button class="btn btn--primary" @click="invite">Пригласить</button>
          </div>
        </div>

        <!-- ACTIONS -->
        <div class="settings-actions">
          <button class="btn btn--primary" @click="save">
            Сохранить
          </button>
        </div>

      </div>

      <div v-else class="profile-loading">
        Загрузка настроек...
      </div>
    </div>
  </div>
</template>

<script>
import { getTypes } from "@/api";

export default {
  name: "SettingsPage",
  data() {
    return {
      user: null,
      allTypes: [], // будут загружены с сервера
      visibleTypes: [],
      allModalElements: [
        { value: "done", label: "Сделано" },
        { value: "calendar", label: "В календарь" },
        { value: "calendarDate", label: "Выбор даты" }
      ],
      visibleElements: [],
      members: [],
      inviteEmail: ""
    };
  },
  async mounted() {
    const savedUser = localStorage.getItem("user");
    if (!savedUser) {
      this.$router.push("/login");
      return;
    }
    this.user = JSON.parse(savedUser);

    // Загружаем все типы заметок с сервера
    try {
      this.allTypes = await getTypes();
    } catch {
      // fallback, если сервер недоступен
      this.allTypes = ["GAME", "HOME", "FILMS", "WISHLIST", "PARENT", "APARTMENT", "TODO", "PROJECT", "PLACE"];
    }

    // Загружаем настройки из localStorage
    const savedSettings = JSON.parse(localStorage.getItem("settings")) || {};
    this.visibleTypes = savedSettings.visibleTypes || [...this.allTypes];
    this.visibleElements = savedSettings.visibleElements || this.allModalElements.map(e => e.value);
    this.members = JSON.parse(localStorage.getItem("groupMembers")) || [];
  },
  methods: {
    save() {
      const settings = {
        visibleTypes: this.visibleTypes,
        visibleElements: this.visibleElements
      };
      localStorage.setItem("settings", JSON.stringify(settings));
      localStorage.setItem("groupMembers", JSON.stringify(this.members));
      alert("Настройки сохранены");
    },
    invite() {
      if (!this.inviteEmail) return;
      this.members.push({ name: this.inviteEmail.split("@")[0], email: this.inviteEmail });
      this.inviteEmail = "";
      alert("Приглашение отправлено (симуляция)");
    }
  }
};
</script>

<style scoped>
.settings-page {
  min-height: 100vh;
  background: var(--ui-bg);
  display: flex;
  justify-content: center;
}

.settings-container {
  width: 100%;
  max-width: 720px;
  margin: 120px auto 80px;
  padding: 0 20px;
}

.settings-card {
  background: var(--ui-surface);
  border: 1px solid var(--ui-border);
  border-radius: var(--ui-radius-lg);
  padding: 36px;
  box-shadow: var(--shadow-sm);
  display: flex;
  flex-direction: column;
  gap: 36px;
}

.settings-header {
  text-align: left;
}

.settings-title {
  margin: 0;
  font-size: 26px;
  font-weight: 700;
}

.settings-subtitle {
  margin-top: 6px;
  font-size: 14px;
  color: var(--ui-text-muted);
}

.settings-section {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.section-title {
  font-size: 14px;
  font-weight: 600;
  color: var(--ui-text-muted);
}

/* GRID FOR CHECKBOXES */
.types-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.type-checkbox {
  background: #fff;
  border: 1px solid var(--ui-border);
  border-radius: 12px;
  padding: 6px 12px;
  cursor: pointer;
  font-size: 14px;
  user-select: none;
  display: flex;
  align-items: center;
  gap: 6px;
}

.type-checkbox input {
  cursor: pointer;
}

/* GROUP */
.group-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.group-member {
  padding: 6px 12px;
  background: #f5f5f5;
  border-radius: 10px;
  font-size: 14px;
  color: var(--ui-text);
}

.group-add {
  display: flex;
  gap: 10px;
  margin-top: 8px;
}

.settings-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}
</style>
