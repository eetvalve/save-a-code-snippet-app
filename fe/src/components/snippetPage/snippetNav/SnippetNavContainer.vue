<template>
  <v-layout class="nav-container">
    <v-card>
      <snippet-nav-search-bar/> <!--emit seach here -->
      <snippet-nav-items-list/> <!--input list updates from here to items list -->
    </v-card>
  </v-layout>
</template>

<script>
  import SnippetNavSearchBar from "./SnippetNavSearchBar";
  import SnippetNavItemsList from "./SnippetNavItemsList";

  export default {
    name: "SnippetNavContainer",
    components: {SnippetNavItemsList, SnippetNavSearchBar}
  }
</script>

<style lang="scss">
  .nav-container {
    width: 20%;
    max-height: 1000px;
    color: $blue;
    margin-right: 10px;
  }
</style>
