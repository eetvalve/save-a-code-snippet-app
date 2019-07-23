<template>
      <div class="snippet-view-container">
      snippets
        <snippet-view-items-list/>
  </div>
</template>

<script>
  import SnippetViewItemsList from "./SnippetViewItemsList";
  export default {
    name: "SnippetViewContainer",
    components: {SnippetViewItemsList}
  }
</script>

<style lang="scss">
  .snippet-view-container {

    border: 1px solid;
  }
</style>
