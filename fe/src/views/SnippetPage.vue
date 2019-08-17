<template>
  <div
    v-if="loadPage"
    class="parent-container">

    <div class="snippet-page-container">
      <snippet-nav-container/>

      <div class="snippet-page-main-content">
        <add-snippet-container/>
        <div id="snippetGap">
          <snippet-view-container/>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
  import {mapState, mapGetter, mapActions, mapMutations} from 'vuex'
  import SnippetNavContainer from "../components/snippetPage/snippetNav/SnippetNavContainer";
  import AddSnippetContainer from "../components/snippetPage/addSnippet/AddSnippetContainer";
  import SnippetViewContainer from "../components/snippetPage/snippetView/SnippetViewContainer";
  import MainHeader from "../components/Header";

  export default {
    name: "SnippetPage",
    components: {MainHeader, SnippetViewContainer, AddSnippetContainer, SnippetNavContainer},
    created() {
      this.isAuthenticated()
      window.addEventListener('scroll', this.handleScroll);
    },
    computed: {
      ...mapState({
        addNewComponentHeight: state => state.snippetData.addNewComponentHeight
      })
    },
    data() {
      return {
        isTopOfPage: true,
        loadPage: false
      }
    },
    methods: {
      isAuthenticated() {
        console.log('inited')
        if (localStorage.getItem('token') === null || localStorage.getItem('user') === null) {
          this.$router.push('/auth')
        } else {
          this.loadPage = true
        }
      },
      handleScroll(event) {

        this.isTopOfPage = window.scrollY <= 0
        const element = document.getElementById('snippetGap')

        if (!this.isTopOfPage && window.innerWidth > 950) {
          console.log('inner width greater that 950')
          element.style.marginTop = this.addNewComponentHeight + "px";
        } else {
          element.style.marginTop = "0px"
        }
      }
    },
    destroyed() {
      window.removeEventListener('scroll', this.handleScroll);
    }
  }
</script>

<style lang="scss">
  .parent-container {
    display: flex;
    flex-direction: column;
    flex-wrap: nowrap;
  }

  .snippet-page-container {
    margin-top: 60px;
    margin-left: auto;
    margin-right: auto;
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    width: 100%;
    max-width: 1000px;
    justify-content: center;

  }

  .snippet-page-main-content {
    display: flex;
    flex-direction: column;
    flex-wrap: nowrap;
    width: 80%;
  }

  .snippetGap {
    margin-top: 95px;
  }

  @media screen and (max-width: 950px) {
    .snippet-page-main-content {
      width: 100%;
    }
  }
</style>
