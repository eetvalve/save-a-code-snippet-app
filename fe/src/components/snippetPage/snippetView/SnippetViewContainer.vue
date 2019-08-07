<template>
  <v-layout
    py-3
    px-1
    column
    nowrap>

    <v-progress-linear
      :active="loading"
      :indeterminate="loading"
      absolute
      bottom
      color="primary accent-4"
      height="2"
      class="ma-0 pa-0"
    ></v-progress-linear>

    <h1 class="font-weight-regular mb-0 mt-3">
      {{ title }}
    </h1>

    <snippet-view-items-list :snippets="snippets"/>
  </v-layout>

</template>

<script>
  import {mapState, mapGetter, mapActions, mapMutations} from 'vuex'
  import SnippetViewItemsList from "./SnippetViewItemsList";

  export default {
    name: "SnippetViewContainer",
    components: {SnippetViewItemsList},
    created() {
      this.getLatestSnippetAdded()
    },
    computed: {
      ...mapState({
        title: state => state.snippetData.selectedTitle,
        snippets: state => state.snippetData.snippets,
        loading: state => state.snippetData.isSnippetsLoading
      })
    },
    methods: {
      getLatestSnippetAdded() {
        this.$store.dispatch('getLatestSnippetsAdded')
      }
    }
  }
</script>

<style lang="scss">
  .snippet-view-container {
    margin-top: 2rem;

  }
</style>
