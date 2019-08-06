<template>
  <div class="mb-2">
    <v-toolbar
      fixed
      class="elevation-0 toolbar-custom">

      <v-toolbar-title
        center
        class="pl-3">
        <div class="title-breakpoint">
          &lt;save-a-code-snippet-app/&gt;
        </div>

        <div class="mobile-title">
          &lt;sn/&gt;
        </div>
      </v-toolbar-title>

      <v-spacer/>

      <v-toolbar-items v-if="user">

        <v-layout
          column
          nowrap
          text-center
          px-2
          class="user-icon-container">
          <v-tooltip
            bottom
            class="tooltip">
            <v-icon
              slot="activator"
              class="user-icon"
              color="primary icon">account_box
            </v-icon>
            <span>user: {{ user.userName }}</span>
          </v-tooltip>

          <span class="header-texts">
            {{ user.userName }}
          </span>
        </v-layout>

        <v-divider vertical/>

        <v-layout
          column
          nowrap
          py-3
          px-3>
          <v-tooltip bottom>
            <v-switch
              slot="activator"
              v-model="isPrivateSnippetsx"
              :label="isPrivateMsgsOn()"
              color="primary"
            />

            <span>{{ isPrivateMsgsOn() }}</span>
          </v-tooltip>
        </v-layout>

        <v-divider vertical/>

        <v-btn
          @click="logout()"
          text
          class="elevation-0 text-none">
          <v-layout
            column
            nowrap
            py-2
            px-2>
            <v-tooltip bottom>
              <v-icon
                slot="activator"
                color="primary icon">exit_to_app
              </v-icon>
              <span>Logout</span>
            </v-tooltip>
            <span class="header-texts">Logout</span>
          </v-layout>
        </v-btn>

      </v-toolbar-items>


    </v-toolbar>
  </div>
</template>

<script>
  import {mapState, mapGetter, mapActions, mapMutations} from 'vuex'

  export default {
    name: "MainHeader",
    computed: {
      ...mapState({
        user: state => state.userData.user,
        isPrivateSnippets: state => state.userData.user.privateSnippets
      }),
      isPrivateSnippetsx: {
        get() {
          return this.isPrivateSnippets
        },
        set(value) {
          this.$store.dispatch('togglePrivacy', this.user.userId)
        }
      }
    },
    methods: {
      isPrivateMsgsOn() {
        const isPrivateMsg = this.isPrivateSnippets ? 'On' : 'Off';
        return 'Private snippets: ' + isPrivateMsg
      },
      logout() {
        this.$store.dispatch('logout')
      }
    }
  }
</script>

<style lang="scss">
  .main-header {
    width: 100vw;
    height: 2rem;
    margin-bottom: 3rem;
  }

  .toolbar-custom {
    z-index: 99999;
  }

  .v-icon--left {
    margin-right: 5px !important;
  }

  .v-toolbar__content {
    padding: 0 !important;
    margin: 0 !important;
  }

  .user-icon-container {
    padding-top: 10px;
  }

  .mobile-title {
    display: none;
  }

  .header-texts {
    text-align: center;
  }

  .tooltip {
    text-align: center;
  }

  @media screen and (max-width: 767px) {
    .header-texts {
      display: none;
    }
    .user-icon-container {
      padding-top: 16px;
    }

    .v-input--selection-controls.v-input .v-label {
      display: none !important;
    }


    .title-breakpoint {
      display: none;
    }

    .mobile-title {
      display: flex;
    }
  }

</style>
