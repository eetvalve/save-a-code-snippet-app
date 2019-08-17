<template>
  <section
    id="add-snippet-container"
    :class="{addSnippetContainer : !isTopOfPage}">
    <v-card
      class=" elevation-5 add-new-borders"
      :class="{ fixedTop : !isTopOfPage }"
    >
      <add-snippet-input-section
        @addNewHasValues="handleAddNewHasValues"
        @cardOpened="isCardOpen"
      />
      <div v-if="cardOpen && showPreview">
        <add-snippet-preview :item="item"/>
      </div>
    </v-card>
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
        item: {},
        isTopOfPage: true
      }
    },
    methods: {
      handleAddNewHasValues(hasValue, itemObject) {
        this.showPreview = hasValue;
        this.item = itemObject;
      },
      isCardOpen(flag) {
        this.cardOpen = flag
      },
      handleScroll(event) {
        // Any code to be executed when the window is scrolled
        const containerHeight = document.getElementById('add-snippet-container').offsetHeight;
        this.$store.commit('GET_ADD_SNIPPET_CONTAINER_HEIGHT', containerHeight)
        this.isTopOfPage = window.scrollY <= 0
      }
    },
    created() {
      window.addEventListener('scroll', this.handleScroll);
    },
    destroyed() {
      window.removeEventListener('scroll', this.handleScroll);
    }
  }
</script>

<style scoped>

  .add-new-borders {
    padding: 8px 5px;
    background-color: #FAFAFA;
    width: inherit;
    max-width: inherit;
  }

  @media screen and (min-width: 950px) {
    .addSnippetContainer {
      width: 91%;
      max-width: 793px;
    }

    .fixedTop {
      position: fixed;
      z-index: 100;
    }
  }
</style>
