<template>
  <v-layout 
    py-3 
    px-1 
    column 
    nowrap>

    <h1 class="font-weight-light mb-0 mt-3">
      Title snippets:
    </h1>

    <snippet-view-items-list/>
  </v-layout>

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
    margin-top: 2rem;

  }
</style>
