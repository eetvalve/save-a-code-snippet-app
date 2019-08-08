<template>
  <v-layout
    column
    wrap>
    <v-card class="elevation-2">

      <v-card-title
        @click="cardOpen = !cardOpen"
        class="headline font-weight-light card-pointer">
        <div v-if="!editModeOn">
          Add new snippet
        </div>
        <div v-else>
          Edit snippet
        </div>

        <v-spacer/>

        <v-btn
          icon>
          <v-icon
            :class="{rotate: cardOpen}"
            color="primary"
            large
          >
            control_point
          </v-icon>
        </v-btn>
      </v-card-title>

      <v-card-text
        v-if="cardOpen"
        class="pb-0 pt-1">

        <v-layout column>

          <add-snippet-title-search
            @filterTitlesList="filterTitlesList"
            @setTitleName="setTitleName"
            :title-list="titleList"
            :existing-title="existingTitle"
          />

          <v-text-field
            v-model="item.description"
            class="text-field-height"
            label="Add Description"
          />


          <v-textarea
            v-model="item.snippet"
            class="textarea-field-height"
            name="input-7-1"
            label="Add Snippet here"
            value=""
            hint="Html elements allowed here"
            rows="1"
            @keyup.enter.native="addNewSnippet()"
          />
        </v-layout>

        <v-card-actions class="pt-0">

          <span
            v-if="isMissingData"
            class="red--text">
            You need to fill all fields
          </span>

          <v-flex class="text-xs-right">
            <v-btn
              v-if="!editModeOn"
              color="primary"
              class="info right"
              @click="addNewSnippet()">
              add
            </v-btn>

            <v-btn
              v-else
              color="primary"
              class="info right"
              @click="editSnippet()">
              edit
            </v-btn>
          </v-flex>
        </v-card-actions>
      </v-card-text>
    </v-card>

    <snack-bar :snackbar="snackbar"/>
  </v-layout>
</template>

<script>
  import {mapState, mapGetter, mapActions, mapMutations} from 'vuex'
  import SnackBar from "../../utils/SnackBar";
  import AddSnippetTitleSearch from "./AddSnippetTitleSearch";

  export default {
    name: "AddSnippetInputSection",
    components: {AddSnippetTitleSearch, SnackBar},
    computed: {
      ...mapState({
        item: state => state.snippetData.snippetTemplate,
        user: state => state.userData.user,
        titleList: state => state.snippetData.titleList,
        snackbar: state => state.snippetData.snackbar,
        editModeOn: state => state.snippetData.editModeOn
      }),
      inputTextLength() {
        return this.item.description.length > 0 || this.item.snippet.length > 0 ? this.$emit('addNewHasValues', true, this.item) : this.$emit('addNewHasValues', false, null)
      },
      cardOpened() {
        return this.$emit('cardOpened', this.cardOpen)
      }
    },
    beforeMount() {
      // this.item.owner.userName = this.user.userName
      // this.item.privateSnippet = this.user.privateSnippet
    },
    data() {
      return {
        cardOpen: false,
        isMissingData: false,
        existingTitle: ''
      }
    },
    watch: {
      inputTextLength(newVal) {
      },
      cardOpened(newVal) {
        if (!this.cardOpen) {
          this.isMissingData = false

          if (this.editModeOn) {
            this.$store.commit('CLEAR_SNIPPET_TEMPLATE')
          }

        }
      },
      editModeOn: {
        handler(isOpenedExternally) {
          if (isOpenedExternally) {
            console.log('this.item.titleName: ', this.item.titleName)
            this.existingTitle = this.item.titleName
          }

          this.cardOpen = isOpenedExternally
        }
      }
    },
    methods: {
      addNewSnippet() {
        const isValid = this.validateInput()

        if (isValid) {
          // TODO api logic here
          this.$store.dispatch('addNewSnippet', this.item)
        } else {
          this.isMissingData = true
        }
      },
      editSnippet() {
        const isValid = this.validateInput()
        if (isValid) {
          // TODO api logic here
          this.$store.dispatch('editSnippet', this.item)
        } else {
          this.isMissingData = true
        }
      },
      validateInput() {
        return !!(this.item.description && this.item.snippet && this.item.titleName)
      },
      filterTitlesList(name) {
        if (name) {
          this.item.titleName = name
          this.$store.dispatch('filterTitlesList', name)
        }
      },
      setTitleName(name) {
        this.item.titleName = name
      }
    },
  }
</script>

<style lang="scss">
  .text-field-height {
    height: 55px !important;
  }

  .rotate {
    transform: rotate(45deg);
  }

  .v-message {
    min-height: 0 !important;
  }

  .card-pointer {
    cursor: pointer;
  }
</style>
