<template>
  <v-text-field
    v-model="text"
    @keyup="filterTitles()"
    hide-details
    append-icon="search"
    single-line
    label="Search by topic"
    class="pa-2"
  />
</template>

<script>
  export default {
    name: "SnippetNavSearchBar",
    data() {
      return {
        text: ''
      }
    },
    methods: {
      filterTitles() {
        return this.$emit('filterTitles', this.text)
      }
    }
  }
</script>

<style scoped>

</style>
