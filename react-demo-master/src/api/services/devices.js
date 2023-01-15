import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allItems() {
    return HTTP.get(BASE_URL + "/devices", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(device) {
    return HTTP.post(BASE_URL + "/devices", device, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(device) {
    return HTTP.patch(BASE_URL + "/devices/" + device.id, device, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  remove(device) {
    return HTTP.delete(BASE_URL + "/devices/" + device.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  allItemsPerUserId(userId) {
    return HTTP.get(BASE_URL + "/devices/allDevices/" + userId, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
