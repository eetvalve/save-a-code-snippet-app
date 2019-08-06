<template>
  <v-layout
    column
    wrap>
    <v-card class="elevation-2">


      <v-card-title class="headline font-weight-light">
        Add new snippet

        <v-spacer/>

        <v-btn
          icon
          @click="cardOpen = !cardOpen">
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
          <v-text-field
            class="text-field-height"
            label="Language/Program name"

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

  export default {
    name: "AddSnippetInputSection",
    components: {SnackBar},
    computed: {
      ...mapState({
        user: state => state.userData.user
      }),
      inputTextLength() {
        return this.item.description.length > 0 || this.item.snippet.length > 0 ? this.$emit('addNewHasValues', true, this.item) : this.$emit('addNewHasValues', false, null)
      },
      cardOpened() {
        return this.$emit('cardOpened', this.cardOpen)
      }
    },
    beforeMount() {
      this.item.owner.userName = this.user.userName
      this.item.privateSnippet = this.user.privateSnippet
    },
    data() {
      return {
        cardOpen: false,
        snackbar: {
          text: 'Snippet added!',
          visible: false
        },
        item: {
          owner: {
            userName: ''
          },
          privateSnippet: false,
          description: '',
          snippet: 'dd'
        }
      }
    },
    watch: {
      inputTextLength(newVal) {
      },
      cardOpened(newVal) {
      },
    },
    methods: {
      addNewSnippet() {

        // todo api logic here

        // finally
        this.snackbar.visible = true;
        this.cardOpen = false;
        this.item = {
          description: '',
          snippet: ''
        }
      }
    },
  }
</script>

<style scoped>
  .text-field-height {
    height: 55px !important;
  }

  .rotate {
    transform: rotate(45deg);
  }
</style>
