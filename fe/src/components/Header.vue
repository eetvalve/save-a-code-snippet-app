<template>
  <v-layout column nowrap class="mb-2">
    <v-toolbar fixed class="elevation-0 toolbar-custom">

      <v-toolbar-title center class="pl-3">
        <div class="title-breakpoint">
          &lt;save-a-code-snippet-app/&gt;
        </div>

        <div class="mobile-title">
          &lt;sn/&gt;
        </div>
      </v-toolbar-title>

      <v-spacer></v-spacer>


      <v-toolbar-items>

        <v-layout column nowrap text-center px-2 class="user-icon-container">
          <v-tooltip bottom class="tooltip">
            <v-icon class="user-icon" slot="activator" color="primary icon">account_box</v-icon>
            <span>user: edu</span>
          </v-tooltip>

          <span class="header-texts">
            edudadadsas
          </span>
        </v-layout>

        <v-divider vertical></v-divider>

        <v-layout column nowrap py-3 px-3>
          <v-tooltip bottom>
            <v-switch
              slot="activator"
              color="primary"
              v-model="switch1"
              :label="isPrivateMsgsOn()"
            ></v-switch>

            <span>{{isPrivateMsgsOn()}}</span>
          </v-tooltip>
        </v-layout>

        <v-divider vertical></v-divider>

        <v-btn text class="elevation-0 text-none">
          <v-layout column nowrap py-2 px-2>
            <v-tooltip bottom>
              <v-icon slot="activator" color="primary icon">exit_to_app</v-icon>
              <span>Logout</span>
            </v-tooltip>
            <span class="header-texts">Logout</span>
          </v-layout>
        </v-btn>

      </v-toolbar-items>


    </v-toolbar>
  </v-layout>
</template>

<script>
  export default {
    name: "main-header",
    data() {
      return {
        switch1: true,
      }
    },
    methods: {
      isPrivateMsgsOn() {
        const isPrivateMsg = this.switch1 ? 'On' : 'Off';
        return 'Private snippets: ' + isPrivateMsg
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
