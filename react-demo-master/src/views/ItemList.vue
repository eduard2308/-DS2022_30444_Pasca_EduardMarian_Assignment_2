<template>
  <v-card>
    <v-card-title>
      Items
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
      :items="devices"
      :search="search"
      @click:row="editItem"
    ></v-data-table>
    <ItemDialogSell
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></ItemDialogSell>
  </v-card>
</template>

<script>
import api from "../api";
import ItemDialogSell from "../components/ItemDialogSell";

export default {
  name: "ItemList",
  components: { ItemDialogSell },
  data() {
    return {
      devices: [],
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
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    editItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    addItem() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.devices = await api.devices.allItemsPerUserId(this.$store.getters["auth/getLoggedUserId"]);
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
