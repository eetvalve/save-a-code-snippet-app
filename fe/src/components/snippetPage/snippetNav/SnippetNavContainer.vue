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
      <v-card>
        <snippet-nav-search-bar/> <!--emit seach here -->
        <snippet-nav-items-list @clicked="onClickChild"/> <!--input list updates from here to items list -->
      </v-card>

      <v-card 
        depressed 
        class="mobile-nav-open elevation-1">
        <v-card-actions>
          <v-btn 
            icon 
            @click="openMobileNav = !openMobileNav">
            <v-icon :class="{rotate : openMobileNav}">arrow_forward_ios</v-icon>
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-layout>

  </v-layout>
</template>

<script>
  import SnippetNavSearchBar from "./SnippetNavSearchBar";
  import SnippetNavItemsList from "./SnippetNavItemsList";

  export default {
    name: "SnippetNavContainer",
    components: {SnippetNavItemsList, SnippetNavSearchBar},
    data() {
      return {
        openMobileNav: false
      }
    },
    methods: {
      onClickChild(value) {
        console.log(value) // someValue

        // close nav
        this.openMobileNav = false

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
  }

  .mobile-nav-open {
    display: none !important;
  }

  @media screen and (max-width: 767px) {

    .nav-container-list {
      position: fixed;
      z-index: 10000;
      height: 100%;
      top: 55px;
    }

    .nav-container-list-closed {
      left: -210px;
    }

    .nav-container-list-open {
      left: 0;
    }

    .mobile-nav-open {
      display: flex !important;
      align-items: center !important;
      justify-content: center;
      width: 25px;

    }

    .nav-container {
      width: 6%;
    }

    .rotate {
      transform: rotate(180deg);
    }

  }
</style>
