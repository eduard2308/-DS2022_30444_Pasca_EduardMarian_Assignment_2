import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allConsumptions() {
    return HTTP.get(BASE_URL + "/energy", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  allConsumptionPerUserId(userId) {
    return HTTP.get(BASE_URL + "/energy/allConsumption/" + userId, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
