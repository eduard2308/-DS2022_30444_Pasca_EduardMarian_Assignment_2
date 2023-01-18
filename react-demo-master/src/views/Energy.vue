<template>
  <v-card>
    <v-card-title>
      Energy
      <v-btn @click="switchToDevices()">Devices</v-btn>
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="energy"
      :search="search"
    ></v-data-table>
  </v-card>
</template>

<script>
import api from "../api";

export default {
  name: "Energy",
  components: {},
  data() {
    return {
      energy: [],
      userIds: [],
      search: "",
      headers: [
        {
          text: "Device",
          align: "start",
          sortable: false,
          value: "deviceId",
        },
        { text: "Date", value: "date" },
        { text: "Consumption", value: "consumption" },
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      //this.energy = await api.energy.allConsumptions();
      this.energy = await api.energy.allConsumptionPerUserId(this.$store.getters["auth/getLoggedUserId"]);
    },
    switchToDevices() {
      this.$router.push("/devices");
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
