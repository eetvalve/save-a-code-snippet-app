<template>
  <v-layout
    row
    nowrap
    class="nav-container">

    <v-layout
      :class="openMobileNav ? 'nav-container-list-open' : 'nav-container-list-closed'"
      row
      nowrap
      class="nav-container-list">
      <v-card class="fixed-desktop-nav">
        <snippet-nav-search-bar
          ref="searchBarComponent"
          @filterTitles="filterTitles"
        />
        <snippet-nav-items-list
          @clicked="getSnippets"
          @getLatest="getLatestSnippets"
          :titles="titles"
          :loading="loading"/>
      </v-card>

      <v-card
        depressed
        class="mobile-nav-open elevation-1">
        <v-card-actions>
          <v-btn
            icon
            @click="openMobileNav = !openMobileNav">
            <v-icon :class="{rotateArrow : openMobileNav}">arrow_forward_ios</v-icon>
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-layout>

  </v-layout>
</template>

<script>
  import {mapState, mapGetter, mapActions, mapMutations} from 'vuex'
  import SnippetNavSearchBar from "./SnippetNavSearchBar";
  import SnippetNavItemsList from "./SnippetNavItemsList";

  export default {
    name: "SnippetNavContainer",
    components: {SnippetNavItemsList, SnippetNavSearchBar},
    created() {
      this.getTitles()
    },
    computed: {
      ...mapState({
        titles: state => state.snippetData.titles,
        loading: state => state.snippetData.isTitlesLoading
      }),
    },
    data() {
      return {
        openMobileNav: false,
      }
    },
    methods: {
      getTitles() {
        this.$store.dispatch('getTitles')
      },
      filterTitles(value) {
        this.$store.dispatch('filterTitles', value)
      },
      getSnippets(value) {
        console.log(value) // someValue

        // close nav
        this.openMobileNav = false
        this.$store.dispatch('getSnippets', value.title)
        this.$refs.searchBarComponent.text = ''
      },
      getLatestSnippets() {
        this.openMobileNav = false
        this.$store.dispatch('getLatestSnippetsAdded')
      }
    }
  }
</script>

<style lang="scss">
  .nav-container {
    width: 20%;
    max-height: 1000px;
    /*  overflow-y: scroll; */
    margin-right: 10px;
    position: relative;
  }

  .mobile-nav-open {
    display: none !important;
  }

  .nav-container-list {

  }

  @media screen and (min-width: 950px) {
    .nav-container-list {
      position: fixed;
      z-index: 10000;
      max-width: 190px;
    }
  }

  @media screen and (max-width: 950px) {

    .nav-container-list {
      position: fixed;
      z-index: 10000;
      top: 55px;
      max-height: 100vh;
    }

    .nav-container-list-closed {
      left: -260px;
    }

    .nav-container-list-open {
      left: 0;
    }

    .mobile-nav-open {
      position: fixed;
      display: flex !important;
      align-items: center !important;
      justify-content: center;
      width: 25px;
      height: 100vh;
    }

    .nav-container {
      width: 6%;
    }

    .rotateArrow {
      transform: rotate(180deg) !important;
    }

  }
</style>
