<template>
  <v-card>
    <v-card-title>
      Users
      <v-btn small @click="switchToItems()">Items</v-btn>
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addUser">Add User</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search"
      @click:row="editUser"
    ></v-data-table>
    <UserDialog
      :opened="dialogVisible"
      :user="selectedUser"
      @refresh="refreshList()"
    ></UserDialog>
    <UserDialogAdd
      :opened="dialogVisibleAdd"
      :user="selectedUserAdd"
      @refresh="refreshList()"
    ></UserDialogAdd>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "../components/UserDialog";
import UserDialogAdd from "@/components/UserDialogAdd";

export default {
  name: "UserList",
  components: { UserDialog, UserDialogAdd },
  data() {
    return {
      users: [],
      search: "",
      headers: [
        { text: "Email", value: "email" },
        { text: "Password", value: "password" },
        { text: "Role", value: "role" },
      ],
      dialogVisible: false,
      dialogVisibleAdd: false,
      selectedUser: {},
      selectedUserAdd: {},
    };
  },
  methods: {
    editUser(user) {
      this.selectedUser = user;
      this.dialogVisible = true;
    },
    addUser(user) {
      this.selectedUserAdd = user;
      this.dialogVisibleAdd = true;
    },
    switchToItems() {
      this.$router.push("/devices/admin");
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedUser = {};
      this.users = await api.users.allUsers();
    },
  },
  async created() {
    this.users = await api.users.allUsers();
  },
};
</script>

<style scoped></style>
