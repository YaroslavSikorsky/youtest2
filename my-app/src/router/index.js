import { createRouter, createWebHistory } from 'vue-router';
// import HomePage from '../views/HomePage.vue';
import HomePage from '../views/HomePage.vue';
import NotesPage from '../views/NotesPage.vue';

const routes = [
    { path: '/', name: 'Home', component: HomePage },
    { path: '/notes', name: 'Notes', component: NotesPage }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;