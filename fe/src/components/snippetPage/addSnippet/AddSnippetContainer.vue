<template>
  <div class="add-snippet-container">
     add new
    <add-snippet-input-section/>
    <add-snippet-preview/> <!-- -->
  </div>

</template>

<script>
  import AddSnippetInputSection from "./AddSnippetInputSection";
  import AddSnippetPreview from "./AddSnippetPreview";
  export default {
    name: "addSnippetContainer",
    components: {AddSnippetPreview, AddSnippetInputSection}
  }
</script>

<style scoped>
  .add-snippet-container {

  }
</style>
