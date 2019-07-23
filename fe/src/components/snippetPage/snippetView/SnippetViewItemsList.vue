<template>
   <ul class="snippet-view-items-list-container">
      <li v-for="item in this.snippets" :key="snippet" class="snippets-list">
         <snippet-view-item :item="item" :index="item.id"/>
      </li>
    </ul>

</template>

<script>
  import SnippetViewItem from "./SnippetViewItem";
  export default {
    name: "SnippetViewItemsList",
    components: {SnippetViewItem}
  }
</script>

<style lang="scss">
  .snippet-view-items-list-container {

  }

  .snippets-list {

  }
</style>
