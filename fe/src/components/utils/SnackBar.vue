<template>
  <v-snackbar
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

<style scoped>

</style>
