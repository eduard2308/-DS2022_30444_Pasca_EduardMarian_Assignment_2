<template>
  <div>
    <v-card>
      <v-card-title>
        Devices
        <v-btn small @click="switchToEnergy()">Energy</v-btn>
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
      ></v-data-table>
    </v-card>
    <v-card>
      <v-card-title>
        <v-data-table>
          :headers="notificationheaders" :items="notifications"
          <template v-slot:item="row">
            <tr>
              <td>
                {{ row.item.message }}
              </td>
            </tr>
          </template>
        </v-data-table>
      </v-card-title>
    </v-card>
  </div>
</template>

<script>
import api from "../api";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "ItemList",
  components: {},
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
        {
          text: "MaxHourlyEnergyConsumption",
          value: "maxHourlyEnergyConsumption",
        },
        { text: "Description", value: "description" },
      ],
      notificationheaders: [{ text: "Notification", value: "notification" }],
      dialogVisible: false,
      selectedItem: {},
      received_messages: [],
      connected: false,
    };
  },
  methods: {
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.devices = await api.devices.allItemsPerUserId(
        this.$store.getters["auth/getLoggedUserId"]
      );
    },
    switchToEnergy() {
      this.$router.push("/energy");
    },
    connect() {
      this.socket = new SockJS("http://localhost:8080/api/websocket");
      this.stompClient = Stomp.over(this.socket);
      // eslint-disable-next-line no-unused-vars
      this.stompClient.connect(
        {},
        (frame) => {
          this.connected = true;
          console.log(frame);
          this.stompClient.subscribe("/topic", (tick) => {
            console.log(tick);
            alert(tick.body);
            this.received_messages.push(JSON.parse(tick.body));
          });
        },
        (error) => {
          console.log(error);
          this.connected = false;
        }
      );
    },
  },
  created() {
    this.connect();
    this.refreshList();
  },
};
</script>

<style scoped></style>
