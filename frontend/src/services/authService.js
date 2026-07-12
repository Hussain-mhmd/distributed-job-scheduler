import api from "../api/axios";

const login = (credentials) => {

    return api.post(
        "/auth/login",
        credentials
    );

};

const register = (user) => {

    return api.post(
        "/auth/register",
        user
    );

};

export default {

    login,
    register

};