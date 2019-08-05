<template>
  <v-snackbar
    v-model="snackbar.visible"
    class="snacbar-custom"
    top
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
    props: {
      snackbar: {
        type: Object
      },

    },
    data() {
      return {

      }
    },
  }
</script>

<style lang="scss">
  .snacbar-custom {
    z-index: 999999;
  }

</style>
