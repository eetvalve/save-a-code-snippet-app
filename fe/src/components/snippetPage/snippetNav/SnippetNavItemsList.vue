<template>
  <v-layout>
    <v-list>
      <template v-for="(item, index) in items">

        <v-list-tile>
          <v-list-tile-content>
            <v-list-tile-title class="list-item-custom" @click="getSnippetsByTitle(item)">
              {{item.title}}
            </v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>

      </template>
    </v-list>
  </v-layout>
</template>

<script>
  export default {
    name: "SnippetNavItemsList",
    data() {
      return {
        items: [
          {
            action: 'local_activity',
            title: 'Attractions',
          },
          {
            action: 'item2',
            title: 'item2 title',
          },
        ]
      }
    },
    methods: {
      getSnippetsByTitle(item) {
        this.$emit('clicked', item)
      }
    }
  }
</script>

<style lang="scss">
  .list-item-custom:hover {
    cursor: pointer;
    color: $blue;
  }
</style>
