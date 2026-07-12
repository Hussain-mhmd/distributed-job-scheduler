import axios from "../api/axios";

export const getQueues = async () => {
    const response = await axios.get("/queues");
    return response.data.data;
};

export const createQueue = async (queue) => {
    const response = await axios.post("/queues", queue);
    return response.data.data;
};

export const deleteQueue = async (id) => {
    await axios.delete(`/queues/${id}`);
};