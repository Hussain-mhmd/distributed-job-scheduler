import api from "../api/axios";

const getMetrics = () => {
    return api.get("/metrics");
};

export default {
    getMetrics,
};