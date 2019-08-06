<template>
  <div class="auth-main-container">
    <v-card v-if="attemptsRemaining > 0">

      <div v-if="showSecureCodeInput">
        <v-card-title class="headline font-weight-light">
          Insert secureCode
        </v-card-title>

        <v-card-text>
          <v-text-field
            class="text-field-height"
            label="code"
            type="password"
            v-model="secureCode"
            @keyup.enter.native="validateCode()"
          />
        </v-card-text>

        <v-card-actions>
          <span class="support-text">Attempts remaining: {{ attemptsRemaining }}</span>
          <v-flex class="text-xs-right">
            <v-btn
              @click="validateCode()"
              class="right"
              color="primary">
              GO
            </v-btn>
          </v-flex>
        </v-card-actions>
      </div>

      <div v-else>
        <v-card-title class="headline font-weight-light">
          Insert username
        </v-card-title>

        <v-card-text>
          <v-text-field
            class="text-field-height"
            label="username"
            hint="You can use pre-exisiting name if you like"
            v-model="userName"
            ref="name"
            @keyup.enter.native="validateUserName()"
          />
        </v-card-text>

        <v-card-actions>
          <span
            v-if="invalidUserName"
            class="support-text">
            Username must be between 3-10 characters long
          </span>

          <v-flex class="text-xs-right">
            <v-btn
              @click="validateUserName()"
              class="right"
              color="primary">
              GO
            </v-btn>
          </v-flex>
        </v-card-actions>
      </div>

    </v-card>
  </div>
</template>

<script>
  import userService from "../services/userService";
  import { mapState, mapGetter, mapActions } from 'vuex'

  export default {
    name: "AuthPage",
    data() {
      return {
        secureCode: '',
        showSecureCodeInput: true,
        attemptsRemaining: this.getRemainingAttempts(),
        isLoginAllowed: true,
        userName: '',
        invalidUserName: false
      }
    },
    computed: {
      ...mapState(['user']),
    },
    methods: {
      ...mapActions(['getUser']),
      validateCode() {
        userService.validateSecureCode({secureCode: this.secureCode})
          .then(res => {
            console.log('CODE: ', res)
            localStorage.setItem('token', res.data)

            this.showSecureCodeInput = false
            this.$nextTick(() => this.$refs.name.$el.focus())

          })
          .catch(err => {
            console.log('err: ', err)
            this.attemptsRemaining = this.countFailedAttempts()
            if (this.attemptsRemaining < 0) {
              this.isLoginAllowed = false
            }
          })
      },
      countFailedAttempts() {
        let failedAttempts = localStorage.getItem('attemptsLeft')

        if (failedAttempts) {
          const parsedAttempts = parseInt(failedAttempts)
          let subtractOne = parsedAttempts - 1;
          localStorage.setItem('attemptsLeft', subtractOne.toString())
        } else {
          localStorage.setItem('attemptsLeft', '3')
        }
        return parseInt(localStorage.getItem('attemptsLeft'))
      },
      getRemainingAttempts() {
        let attemptsLeft = localStorage.getItem('attemptsLeft')

        if (attemptsLeft) {
          return parseInt(attemptsLeft)
        }
        localStorage.setItem('attemptsLeft', '3');
        return 3
      },
      validateUserName() {
        if (this.userName.length >= 3 && this.userName.length <= 10) {
          this.getUser(this.userName)
        } else {
          this.invalidUserName = true
        }
      }
    }
  }
</script>

<style lang="scss">
  .auth-main-container {
    margin-left: auto;
    margin-right: auto;
    margin-top: 200px;
    width: 30vw;
    max-width: 600px;
    min-width: 330px;
  }

  .support-text {
    color: $support-text;
  }
</style>
