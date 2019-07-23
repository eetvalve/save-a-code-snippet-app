<template>
  <div class="nav-container">
    <snippet-nav-search-bar/> <!--emit seach here -->
    <snippet-nav-items-list/> <!--input list updates from here to items list -->
  </div>
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
    min-height: 1000px;
    border: 1px solid black;
    color: $blue;
  }
</style>
