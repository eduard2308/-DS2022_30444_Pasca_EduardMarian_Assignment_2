<template>
  <v-card>
    <v-card-title>
      Items
      <v-btn small @click="switchToUsers()">Users</v-btn>
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addItem">Add Item</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="devices"
      :search="search"
      @click:row="editItem"
    ></v-data-table>
    <DeviceDialog
      :opened="dialogVisible"
      :device="selectedItem"
      :userIds="userIds"
      @refresh="refreshList"
    ></DeviceDialog>
  </v-card>
</template>

<script>
import api from "../api";
import DeviceDialog from "@/components/DeviceDialog";

export default {
  name: "DevicesAdmin",
  components: { DeviceDialog },
  data() {
    return {
      devices: [],
      userIds: [],
      search: "",
      headers: [
        {
          text: "Name",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Address", value: "address" },
        { text: "MaxHourlyEnergyConsumption", value: "maxHourlyEnergyConsumption" },
        { text: "Description", value: "description" },
        { text: "User", value: "userId"},
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    editItem(device) {
      this.selectedItem = device;
      this.dialogVisible = true;
    },
    addItem() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.devices = await api.devicesAdmin.allItems();
      this.userIds = await api.users.allIds();
    },
    switchToUsers() {
      this.$router.push("/users");
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
