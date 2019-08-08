<template>

  <v-card class="mb-3">
    <v-card-title>

      <v-flex
        md10
        sm10>
        <h3 class="font-weight-medium">
          {{ item.description }}
        </h3>
      </v-flex>


      <v-flex
        md2
        sm2
        xs4>
        <v-card-actions>
          <v-btn
            :disabled="isPreview"
            small
            icon
            class="v-btn--outline"
            color="primary">
            <v-tooltip bottom>
              <v-icon
                slot="activator"
                small
                color="primary">account_box
              </v-icon>
              <span v-if="!isPreview">Creator: {{ item.owner.userName }}
                <br>
                is private: {{ item.privateSnippet }}
              </span>
            </v-tooltip>
          </v-btn>

          <v-btn
            @click="editSnippet()"
            :disabled="isPreview"
            small
            text
            icon
            color="primary">
            <v-tooltip bottom>
              <v-icon
                slot="activator"
                small>edit
              </v-icon>
              <span>edit</span>
            </v-tooltip>
          </v-btn>

          <v-btn
            @click="deleteSnippet()"
            :disabled="isPreview"
            small
            text
            icon
            color="error">
            <v-tooltip bottom>
              <v-icon
                slot="activator"
                small>delete
              </v-icon>
              <span>delete</span>
            </v-tooltip>
          </v-btn>
        </v-card-actions>
      </v-flex>

    </v-card-title>

    <v-card-text class="pt-0">

      <div
        :id="'snippet-position-' + index"
        class="snippet-content-container pa-2">
        <!-- {{item.snippet}} -->
      </div>


    </v-card-text>

    <snack-bar :snackbar="snackbar" />
  </v-card>

</template>

<script>
  import {mapState, mapGetter, mapActions, mapMutations} from 'vuex'
  import SnackBar from "../../utils/SnackBar";

  export default {
    name: "SnippetViewItem",
    components: {SnackBar},
    computed: {
      ...mapState({
        snackbar: state => state.snippetData.snackbar
      })
    },
    props: {
      item: {
        type: Object,
      },
      isPreview: {
        type: Boolean
      },
      index: {
        type: Number
      }
    },
    data() {
      return {
        filterBtnTTip: false
      }
    },
    watch: {
      item: {
        // the callback will be called immediately after the start of the observation
        // immediate: true,
        handler(newVal, oldVal) {
          let val = newVal ? newVal : oldVal;

          this.stringToHtml(val)

        },
        deep: true
      }
    },
    mounted() {
      this.stringToHtml(this.item)
    },
    methods: {
      stringToHtml(item) {
        const injectPosition = document.getElementById("snippet-position-" + this.index)
        injectPosition.innerHTML = item.snippet;
      },
      deleteSnippet() {
        this.$store.dispatch('deleteSnippet', this.item.snippetId)
      },
      editSnippet() {
        this.$store.commit('UPDATE_SNIPPET_TEMPLATE', this.item)
      }
    },
  }
</script>

<style lang="scss">
  .snippet-content-container {
    background-color: $description-grey;
    border-radius: 6px;
  }
</style>
