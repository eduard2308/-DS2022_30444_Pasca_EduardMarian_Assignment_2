<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create device" : "Edit device" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="device.name" label="Name" />
          <v-text-field v-model="device.address" label="Address" />
          <v-text-field
            v-model="device.maxHourlyEnergyConsumption"
            label="MaxHourlyEnergyConsumption"
          />
          <v-text-field v-model="device.description" label="Description" />

          <v-select
            :items="userIds"
            @change="onChange($event)"
            menu-props="auto"
            label="User"
            hide-details
            single-line
          ></v-select>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="remove" v-if="isNew === false">
            {{ "Delete" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "DeviceDialog",
  props: {
    device: Object,
    opened: Boolean,
    userIds: [],
  },
  data() {
    return {
      selectedId: {},
      onChange(e) {
        this.selectedId = e;
      },
    };
  },
  methods: {
    remove() {
      api.devicesAdmin
        .remove({
          id: this.device.id,
          name: this.device.name,
          address: this.device.address,
          maxHourlyEnergyConsumption: this.device.maxHourlyEnergyConsumption,
          description: this.device.description,
          userId: this.device.userId,
        })
        .then(() => this.$emit("refresh"));
    },
    persist() {
      if (this.isNew) {
        api.devicesAdmin
          .create({
            name: this.device.name,
            address: this.device.address,
            maxHourlyEnergyConsumption: this.device.maxHourlyEnergyConsumption,
            description: this.device.description,
            userId: this.selectedId,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.devicesAdmin
          .edit({
            id: this.device.id,
            name: this.device.name,
            address: this.device.address,
            maxHourlyEnergyConsumption: this.device.maxHourlyEnergyConsumption,
            description: this.device.description,
            userId: this.device.userId,
          })
          .then(() => this.$emit("refresh"));
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.device.id;
    },
  },
};
</script>

<style scoped></style>
