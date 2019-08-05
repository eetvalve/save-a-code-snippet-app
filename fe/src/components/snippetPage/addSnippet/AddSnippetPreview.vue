<template>
  <v-card class="card-color mt-0">
    <v-card-title>
      <h3 class="font-weight-light">
        Preview
      </h3>
    </v-card-title>

    <v-card-text>
      <snippet-view-item 
        :item="item" 
        :is-preview="true"/>
    </v-card-text>
  </v-card>
</template>

<script>
  import SnippetViewItem from "../snippetView/SnippetViewItem";

  export default {
    name: "AddSnippetPreview",
    components: {SnippetViewItem},
    props: {
      item: {
        type: Object
      }
    }
  }
</script>

<style lang="scss">
  .card-color {
    background-color: $yellow !important;
  }
</style>
