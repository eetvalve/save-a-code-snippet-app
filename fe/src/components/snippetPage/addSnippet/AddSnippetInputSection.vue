<template>
  <v-layout
    column
    wrap>
    <v-card class="elevation-2">


      <v-card-title
        @click="cardOpen = !cardOpen"
        class="headline font-weight-light card-pointer">
        Add new snippet

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
              color="primary"
              class="info right"
              @click="addNewSnippet()">
              add
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
        user: state => state.userData.user,
        titleList: state => state.snippetData.titleList
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
        snackbar: {
          text: 'Snippet added!',
          visible: false
        },
        item: {
          titleName: '',
          privateSnippet: false,
          description: '',
          snippet: ''
        }
      }
    },
    watch: {
      inputTextLength(newVal) {
      },
      cardOpened(newVal) {
        if (!this.cardOpen) {
          this.isMissingData = false
        }
      },
    },
    methods: {
      addNewSnippet() {
        const isValid = this.validateInput()

        if (isValid) {
          // TODO api logic here
          this.$store.dispatch('addNewSnippet', this.item)
            .then(res => {
              // finally
              this.snackbar.visible = true;
              // this.cardOpen = false;
              this.item.description = ''
              this.item.snippet = ''
              this.isMissingData = false
            })
            .catch(err => {
              console.log('err: ', err)
              this.snackbar.text = 'snippet addition failed, try again later'
              this.snackbar.visible = true
            })


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
