<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> Add user </v-toolbar>
        <v-form>
          <v-text-field v-model="user.email" label="Email" />
          <v-text-field v-model="user.password" label="Password" />
          <v-text-field v-model="user.role" label="Role" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist()"> Save </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "UserDialogAdd",
  props: {
    user: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.users
          .create({
            email: this.user.email,
            password: this.user.password,
            role: this.user.role,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.users
          .edit({
            id: this.user.id,
            email: this.user.email,
            password: this.user.password,
            role: this.user.role,
          })
          .then(() => this.$emit("refresh"));
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.user.id;
    },
  },
};
</script>

<style scoped></style>
