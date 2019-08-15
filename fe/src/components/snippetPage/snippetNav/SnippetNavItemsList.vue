<template>
  <v-list class="list-height">

    <v-list-tile>
      <v-list-tile-content>
        <v-list-tile-title
          class="list-item-custom"
          @click="getSnippetsByLatest()">
          Latest addition
        </v-list-tile-title>
      </v-list-tile-content>
    </v-list-tile>

    <div class="divider-centerer">
      <v-divider></v-divider>

      <v-progress-linear
        :active="loading"
        :indeterminate="loading"
        absolute
        bottom
        color="primary accent-4"
        height="2"
        class="ma-0 pa-0"
      ></v-progress-linear>
    </div>


    <div class="list-overflow">
      <v-list-tile
        v-for="(item, index) in titles">
        <v-list-tile-content>
          <v-list-tile-title
            class="list-item-custom"
            @click="getSnippetsByTitle(item)">
            {{ item.title.title }}
          </v-list-tile-title>
        </v-list-tile-content>
      </v-list-tile>
    </div>
  </v-list>

</template>

<script>
  export default {
    name: "SnippetNavItemsList",
    props: {
      titles: {
        type: Array,
        default: () => []
      },
      loading: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {}
    },
    methods: {
      getSnippetsByTitle(item) {
        this.$emit('clicked', item)
        this.loading = false
      },
      getSnippetsByLatest() {
        this.$emit('getLatest')
      }
    }
  }
</script>

<style lang="scss">
  .list-item-custom:hover {
    cursor: pointer;
    color: $blue;
  }

  .divider-centerer {
    margin-left: 10px;
    margin-right: 10px;
  }

  .list-height {
    height: inherit;
  }

  .list-overflow {
    max-width: 190px;
    overflow-y: auto;
    max-height: 70vh;
  }

  .list-overflow::-webkit-scrollbar-track {

  }

  .list-overflow::-webkit-scrollbar {
    width: 5px;
  }

  .list-overflow::-webkit-scrollbar-thumb {
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
    background-color: $support-text;
  }

  @media screen and (max-width: 950px) {
    .list-overflow {
      max-width: 226px;
      height: 80vh !important;
      overflow-y: auto !important;
    }
  }
</style>
