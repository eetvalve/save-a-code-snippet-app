<template>
  <section class="add-snippet-container">
    <add-snippet-input-section
      @addNewHasValues="handleAddNewHasValues"
      @cardOpened="isCardOpen"
    />
    <div v-if="cardOpen && showPreview">
      <add-snippet-preview :item="item"/> <!-- -->
    </div>
  </section>

</template>

<script>

  import AddSnippetInputSection from "./AddSnippetInputSection";
  import AddSnippetPreview from "./AddSnippetPreview";

  export default {
    name: "AddSnippetContainer",
    components: {AddSnippetPreview, AddSnippetInputSection},
    data() {
      return {
        showPreview: false,
        cardOpen: false,
        item: {}
      }
    },
    methods: {
      handleAddNewHasValues(hasValue, itemObject) {
        this.showPreview = hasValue;
        this.item = itemObject;
      },
      isCardOpen(flag) {
        console.log('cardOpen: ', flag)
        this.cardOpen = flag
      }
    }
  }
</script>

<style scoped>
  .add-snippet-container {

  }
</style>
