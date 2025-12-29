<template>
  <div id="app">
    <AppHeader/>
    <router-view/>
  </div>
</template>

<script>
import AppHeader from "@/components/AppHeader.vue";

export default {
  name: "App",
  components: {AppHeader}
};
</script>

<style>
html, body, #app {
  height: 100%;
  margin: 0;
  padding: 0;
  background: #f8f9fc;
  font-family: 'Inter', sans-serif;
}
</style>
