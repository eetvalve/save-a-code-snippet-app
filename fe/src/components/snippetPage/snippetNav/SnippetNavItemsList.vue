<template>
  <v-layout>
    <v-list>
      <template v-for="(item, index) in items">

        <v-list-tile>
          <v-list-tile-content>
            <v-list-tile-title >
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
    }
  }
</script>

<style scoped>

</style>
