import axios from "../api/axios";

export const getJobs = async () => {
    const response = await axios.get("/jobs");
    return response.data.data;
};

export const createJob = async (job) => {
    const response = await axios.post("/jobs", job);
    return response.data.data;
};

export const updateJob = async (id, job) => {
    const response = await axios.put(`/jobs/${id}`, job);
    return response.data.data;
};

export const deleteJob = async (id) => {
    await axios.delete(`/jobs/${id}`);
};