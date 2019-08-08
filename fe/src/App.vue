<template>
  <div id="app">
    <v-app>
      <!--
      <div id="nav">
        <router-link to="/">Home</router-link> |
        <router-link to="/about">About</router-link>
      </div>
      -->
      <main-header/>
      <router-view/>
    </v-app>
  </div>
</template>

<style lang="scss">
  #app {
    background-color: $grey;
  }

  #nav {
  }
</style>
<script>
  import MainHeader from "./components/Header";
  export default {
    components: {MainHeader}
  }
</script>
