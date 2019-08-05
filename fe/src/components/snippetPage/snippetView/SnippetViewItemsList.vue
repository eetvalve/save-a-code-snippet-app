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
      <template v-for="(item, index) in items">

        <snippet-view-item 
          :item="item" 
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
    data() {
      return {
        items: [
          {
            id: 0,
            description: "taa on hyva",
            snippet: "u + v",
            owner: "edu"
          },
          {
            id: 1,
            description: "taa on hyva 2",
            snippet: "u + v",
            owner: "edu"
          },
          {
            id: 2,
            description: "taa on hyva 3",
            snippet: "u + v",
            owner: "edu"
          },
        ]
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
