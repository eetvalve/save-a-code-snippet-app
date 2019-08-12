<template>
  <div class="title-search-container">
    <v-text-field
      @keyup="filterTitlesList()"
      @focus="focusIn = true"
      @blur="removeFocus()"
      v-model="titleName"
      class="text-field-height"
      label="Language/Program name"
      hint="Use pre-existing or create new"></v-text-field>


    <div v-if="titleList.length > 0 && (focusIn || listFocus)">

      <br/>

      <v-card
        class="title-list-container">
        <v-list-tile
          v-for="(title, index) in titleList">
          <v-btn
            @click="selectTitle(title)"
            @focus="putFocus()"
            @blur="removeListFocus()"
            flat
            block>
            {{ title }}
          </v-btn>
        </v-list-tile>

      </v-card>
    </div>
  </div>
</template>

<script>

  export default {
    name: "AddSnippetTitleSearch",
    props: {
      titleList: {
        type: Array
      },
      existingTitle: {
        type: String
      }
    },
    watch: {
      existingTitle: {
        immediate: true,
        handler(val) {
          if (val) {
            console.log('existingTitle: ', val)
            this.titleName = val
          }
        }
      }
    },
    data() {
      return {
        titleName: '',
        focusIn: false,
        listFocus: false
      }
    },
    computed: {},
    methods: {
      filterTitlesList() {
        this.$emit('filterTitlesList', this.titleName)
      },
      selectTitle(title) {
        this.titleName = title
        this.$emit('setTitleName', this.titleName)
        this.focusIn = false
        this.listFocus = false
      },
      removeFocus() {
        setTimeout(() => {
          this.focusIn = false
        }, 200)
      },
      removeListFocus() {
        this.listFocus = false
      },
      putFocus() {
        this.listFocus = true
      }
    }
  }
</script>

<style lang="scss">
  .title-search-container {
    position: relative;
  }

  .title-list-container {
    //  position: absolute !important;
    z-index: 100;
    width: 70%;
    max-height: 200px;
    overflow-y: scroll;
  }
</style>
