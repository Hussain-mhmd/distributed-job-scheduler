import { useState } from "react";
import api from "../api/axios";

function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const login = async () => {

        try {

            const res = await api.post("/auth/login", {
                email,
                password
            });

            localStorage.setItem("token", res.data.data.token);

            window.location.reload();

        } catch (e) {

            console.log(e);

            console.log(e.response);

            alert(
                e.response?.data?.message ||
                e.message ||
                "Login Failed"
            );

        }

    };

    return (

        <div style={{padding:"50px"}}>

            <h2>Distributed Job Scheduler</h2>

            <input
                placeholder="Email"
                onChange={(e)=>setEmail(e.target.value)}
            />

            <br/><br/>

            <input
                type="password"
                placeholder="Password"
                onChange={(e)=>setPassword(e.target.value)}
            />

            <br/><br/>

            <button onClick={login}>Login</button>

        </div>

    );

}

export default Login;