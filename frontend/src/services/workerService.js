import axios from "../api/axios";

export const getWorkers = async () => {
    const response = await axios.get("/workers");
    return response.data.data;
};