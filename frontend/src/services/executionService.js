import axios from "../api/axios";

export const getExecutionHistory = async () => {
    const response = await axios.get("/job-executions");
    return response.data.data;
};