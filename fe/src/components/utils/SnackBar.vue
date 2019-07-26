<template>
  <v-snackbar
    class="snacbar-custom"
    top
    v-model="snackbar.visible"
  >
    {{ snackbar.text }}
    <v-btn
      color="primary"
      text
      @click="snackbar.visible = false"
    >
      Close
    </v-btn>
  </v-snackbar>
</template>

<script>
  export default {
    name: "SnackBar",
    data() {
      return {

      }
    },
    props: {
      snackbar: {
        type: Object
      },

    }
  }
</script>

<style lang="scss">
  .snacbar-custom {
    z-index: 999999;
  }

</style>
