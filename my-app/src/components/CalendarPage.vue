<template>
  <div class="calendar-page">
    <div class="calendar-container">
      <div class="calendar-layout">
        <!-- LEFT: CALENDAR -->
        <div class="calendar-left">
          <!-- HEADER -->
          <div class="calendar-header">
            <button class="btn-nav" @click="prevMonth">‹</button>
            <h2 class="calendar-title">
              {{ monthName }} {{ currentYear }}
            </h2>
            <button class="btn-nav" @click="nextMonth">›</button>
          </div>

          <!-- WEEKDAYS -->
          <div class="calendar-weekdays">
            <div v-for="day in weekdays" :key="day" class="weekday">
              {{ day }}
            </div>
          </div>

          <!-- DAYS -->
          <div class="calendar-days">
            <div
                v-for="(day, index) in calendarDays"
                :key="index"
                class="day-cell"
                :class="{
                'day-cell--other': day.isOtherMonth,
                'day-cell--today': day.isToday,
                'day-cell--selected': day.isSelected,
                'day-cell--has-notes': day.hasNotes
              }"
                @click="selectDate(day)"
            >
              <div class="day-number">{{ day.day }}</div>
              <div v-if="day.notesCount > 0" class="day-notes-badge">
                {{ day.notesCount }}
              </div>
            </div>
          </div>
        </div>

        <!-- RIGHT: NOTES -->
        <div class="calendar-right">
          <div v-if="!selectedDate" class="notes-empty">
            Выберите день в календаре
          </div>

          <div v-else class="selected-date-notes">
            <h3 class="selected-date-title">
              Заметки на {{ formatDate(selectedDate) }}
            </h3>

            <div v-if="loading" class="notes-loading">
              Загрузка...
            </div>

            <div v-else-if="selectedDateNotes.length === 0" class="notes-empty">
              На эту дату заметок нет
            </div>

            <div v-else class="notes-list">
              <div
                  v-for="note in selectedDateNotes"
                  :key="note.id"
                  class="note-item"
                  @click="openNote(note)"
              >
                <div class="note-item__header">
                  <span class="note-item__title">{{ note.title }}</span>
                  <span class="note-item__type">{{ note.type }}</span>
                </div>
                <div class="note-item__text">{{ note.text }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- MODAL -->
    <NoteDetailsModal
        v-if="activeNote"
        :note="activeNote"
        @close="closeNote"
        @changed="loadCalendarNotes"
    />
  </div>
</template>

<script>
import { getCalendarNotes, getNotesByDate } from "@/api";
import NoteDetailsModal from "@/components/NoteDetailsModal.vue";

export default {
  name: "CalendarPage",
  components: { NoteDetailsModal },

  data() {
    const now = new Date();
    return {
      currentMonth: now.getMonth(),
      currentYear: now.getFullYear(),
      selectedDate: null,
      calendarNotes: [],
      selectedDateNotes: [],
      loading: false,
      activeNote: null,
      weekdays: ["Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"]
    };
  },

  computed: {
    monthName() {
      return [
        "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
        "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
      ][this.currentMonth];
    },

    calendarDays() {
      const year = this.currentYear;
      const month = this.currentMonth;

      const firstDay = new Date(year, month, 1);
      const lastDay = new Date(year, month + 1, 0);

      let firstDayWeek = firstDay.getDay() - 1;
      if (firstDayWeek === -1) firstDayWeek = 6;

      const days = [];
      const today = new Date();
      today.setHours(0, 0, 0, 0);

      const prevMonthLastDay = new Date(year, month, 0).getDate();
      for (let i = firstDayWeek - 1; i >= 0; i--) {
        const day = prevMonthLastDay - i;
        const date = new Date(year, month - 1, day);
        days.push({
          day,
          date,
          isOtherMonth: true,
          isToday: false,
          isSelected: false,
          hasNotes: this.hasNotesOnDate(date),
          notesCount: this.getNotesCountOnDate(date)
        });
      }

      for (let day = 1; day <= lastDay.getDate(); day++) {
        const date = new Date(year, month, day);
        const isToday = date.getTime() === today.getTime();
        const isSelected =
            this.selectedDate &&
            date.getTime() === new Date(this.selectedDate).getTime();

        days.push({
          day,
          date,
          isOtherMonth: false,
          isToday,
          isSelected,
          hasNotes: this.hasNotesOnDate(date),
          notesCount: this.getNotesCountOnDate(date)
        });
      }

      const remainingDays = 42 - days.length;
      for (let day = 1; day <= remainingDays; day++) {
        const date = new Date(year, month + 1, day);
        days.push({
          day,
          date,
          isOtherMonth: true,
          isToday: false,
          isSelected: false,
          hasNotes: this.hasNotesOnDate(date),
          notesCount: this.getNotesCountOnDate(date)
        });
      }

      return days;
    }
  },

  async mounted() {
    await this.loadCalendarNotes();
  },

  methods: {
    async loadCalendarNotes() {
      const user = JSON.parse(localStorage.getItem("user"));
      if (!user?.id) return;

      this.calendarNotes = await getCalendarNotes(user.id);
    },

    hasNotesOnDate(date) {
      const dateStr = this.dateToString(date);
      return this.calendarNotes.some(n => n.calendarDate === dateStr);
    },

    getNotesCountOnDate(date) {
      const dateStr = this.dateToString(date);
      return this.calendarNotes.filter(n => n.calendarDate === dateStr).length;
    },

    async selectDate(dayObj) {
      this.selectedDate = dayObj.date;
      const user = JSON.parse(localStorage.getItem("user"));
      if (!user?.id) return;

      this.loading = true;
      try {
        this.selectedDateNotes = await getNotesByDate(
            user.id,
            this.dateToString(dayObj.date)
        );
      } finally {
        this.loading = false;
      }
    },

    dateToString(date) {
      return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(
          2,
          "0"
      )}-${String(date.getDate()).padStart(2, "0")}`;
    },

    formatDate(date) {
      return new Date(date).toLocaleDateString("ru-RU", {
        day: "numeric",
        month: "long",
        year: "numeric"
      });
    },

    prevMonth() {
      this.currentMonth === 0
          ? ((this.currentMonth = 11), this.currentYear--)
          : this.currentMonth--;
      this.selectedDate = null;
      this.selectedDateNotes = [];
    },

    nextMonth() {
      this.currentMonth === 11
          ? ((this.currentMonth = 0), this.currentYear++)
          : this.currentMonth++;
      this.selectedDate = null;
      this.selectedDateNotes = [];
    },

    openNote(note) {
      this.activeNote = note;
    },

    closeNote() {
      this.activeNote = null;
    }
  }
};
</script>

<style scoped>
/* PAGE */
.calendar-page {
  height: calc(100vh - 62px);
  margin-top: 62px;
  background: var(--ui-bg);
  display: flex;
  overflow: hidden;
}

/* CONTAINER */
.calendar-container {
  max-width: 1200px;
  margin: 0 auto;
  background: var(--ui-surface);
  border-radius: 16px;
  padding: 32px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);

  flex: 1;
  display: flex;
}

/* MAIN LAYOUT */
.calendar-layout {
  display: grid;
  grid-template-columns: 420px 1fr;
  gap: 32px;
  width: 100%;
  height: 100%;
}

/* LEFT COLUMN */
.calendar-left {
  display: flex;
  flex-direction: column;
  min-height: 0;
}

/* HEADER */
.calendar-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.calendar-title {
  font-size: 22px;
  font-weight: 700;
  color: var(--ui-primary);
  margin: 0;
}

.btn-nav {
  background: transparent;
  border: 1px solid var(--ui-border);
  width: 40px;
  height: 40px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 20px;
  color: var(--ui-primary);
  transition: all 0.2s;
}

.btn-nav:hover {
  background: var(--ui-primary);
  color: #fff;
  border-color: var(--ui-primary);
}

/* WEEKDAYS */
.calendar-weekdays {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 8px;
  margin-bottom: 8px;
}

.weekday {
  text-align: center;
  font-size: 13px;
  font-weight: 600;
  color: var(--ui-text-muted);
  padding: 6px 0;
}

/* DAYS GRID */
.calendar-days {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  grid-auto-rows: 1fr;
  gap: 8px;

  flex: 1;
  max-height: 520px; /* ключевой момент */
}

/* DAY CELL */
.day-cell {
  display: flex;
  align-items: center;
  justify-content: center;

  border-radius: 8px;
  border: 1px solid var(--ui-border);
  cursor: pointer;
  transition: all 0.2s;
  position: relative;
  background: #fff;
}

.day-cell:hover {
  background: rgba(107, 124, 255, 0.05);
  border-color: var(--ui-primary);
}

.day-cell--other {
  color: var(--ui-text-muted);
  opacity: 0.4;
}

.day-cell--today {
  background: rgba(107, 124, 255, 0.1);
  border-color: var(--ui-primary);
  font-weight: 700;
}

.day-cell--selected {
  background: var(--ui-primary);
  color: #fff;
  border-color: var(--ui-primary);
}

.day-cell--has-notes {
  border-color: #10b981;
  border-width: 2px;
}

.day-number {
  font-size: 15px;
}

.day-notes-badge {
  position: absolute;
  top: 4px;
  right: 4px;
  background: #10b981;
  color: #fff;
  font-size: 10px;
  font-weight: 700;
  width: 18px;
  height: 18px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* RIGHT COLUMN */
.calendar-right {
  border-left: 1px solid var(--ui-border);
  padding-left: 24px;

  display: flex;
  flex-direction: column;
  min-height: 0;
}

/* NOTES */
.selected-date-notes {
  display: flex;
  flex-direction: column;
  flex: 1;
  min-height: 0;
}

.selected-date-title {
  font-size: 18px;
  font-weight: 700;
  color: var(--ui-primary);
  margin: 0 0 16px 0;
}

.notes-loading,
.notes-empty {
  text-align: center;
  padding: 24px;
  color: var(--ui-text-muted);
  font-size: 14px;
}

.notes-list {
  flex: 1;
  overflow-y: auto;
  padding-right: 8px;

  display: flex;
  flex-direction: column;
  gap: 12px;
}

/* NOTE ITEM */
.note-item {
  background: var(--ui-bg);
  border: 1px solid var(--ui-border);
  border-radius: 10px;
  padding: 16px;
  cursor: pointer;
  transition: all 0.2s;
}

.note-item:hover {
  border-color: var(--ui-primary);
  box-shadow: 0 4px 12px rgba(107, 124, 255, 0.1);
}

.note-item__header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
  gap: 12px;
}

.note-item__title {
  font-weight: 600;
  color: var(--ui-text);
  font-size: 15px;
}

.note-item__type {
  font-size: 11px;
  font-weight: 600;
  background: rgba(107, 124, 255, 0.1);
  color: var(--ui-primary);
  padding: 4px 8px;
  border-radius: 6px;
  text-transform: uppercase;
}

.note-item__text {
  font-size: 14px;
  color: var(--ui-text-muted);
  line-height: 1.5;
  max-height: 40px;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

/* MOBILE */
@media (max-width: 768px) {
  .calendar-page {
    height: auto;
    display: block;
    overflow: visible;
  }

  .calendar-container {
    display: block;
  }

  .calendar-layout {
    grid-template-columns: 1fr;
    height: auto;
  }

  .calendar-right {
    border-left: none;
    padding-left: 0;
    margin-top: 24px;
  }

  .calendar-days {
    max-height: none;
  }
}
</style>

