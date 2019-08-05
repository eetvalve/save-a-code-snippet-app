<template>
  <div class="parent-container">

    <div class="snippet-page-container">
      <snippet-nav-container/>

      <div class="snippet-page-main-content">
        <add-snippet-container/>
        <snippet-view-container/>
      </div>

    </div>
  </div>
</template>

<script>
  import SnippetNavContainer from "../components/snippetPage/snippetNav/SnippetNavContainer";
  import AddSnippetContainer from "../components/snippetPage/addSnippet/AddSnippetContainer";
  import SnippetViewContainer from "../components/snippetPage/snippetView/SnippetViewContainer";
  import MainHeader from "../components/Header";

  export default {
    name: "SnippetPage",
    components: {MainHeader, SnippetViewContainer, AddSnippetContainer, SnippetNavContainer}
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

  @media screen and (max-width: 767px) {
    .snippet-page-main-content {
      width: 100%;
    }
  }
</style>
