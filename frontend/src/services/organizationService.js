import axios from "../api/axios";

export const getOrganizations = async () => {
    const response = await axios.get("/organizations");
    return response.data.data;
};

export const createOrganization = async (organization) => {
    const response = await axios.post("/organizations", organization);
    return response.data.data;
};

export const updateOrganization = async (id, organization) => {
    const response = await axios.put(`/organizations/${id}`, organization);
    return response.data.data;
};

export const deleteOrganization = async (id) => {
    await axios.delete(`/organizations/${id}`);
};