import axios from "../api/axios";

export const getDeadLetterJobs = async () => {
    const response = await axios.get("/dead-letter");
    return response.data;
};