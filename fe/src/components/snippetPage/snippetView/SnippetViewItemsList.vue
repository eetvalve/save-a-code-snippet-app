<template>
  <!--
   <ul class="snippet-view-items-list-container">
      <li v-for="item in this.snippets" :key="snippet" class="snippets-list">
         <snippet-view-item :item="item" :index="item.id"/>
      </li>
    </ul>
    -->

  <v-layout>
    <v-list class="list-width custom-color">
      <template v-for="(item, index) in snippets">

        <snippet-view-item 
          :item="item"
          :index="index"
          :is-preview="false"/>

      </template>
    </v-list>
  </v-layout>

</template>

<script>
  import SnippetViewItem from "./SnippetViewItem";

  export default {
    name: "SnippetViewItemsList",
    components: {SnippetViewItem},
    props: {
      snippets: {
        type: Array,
        default: () => []
      }
    },
    data() {
      return {
      }
    }
  }
</script>

<style lang="scss">
  .list-width {
    width: 100% !important;
  }

  .custom-color {
    background: $grey !important;
  }
</style>
