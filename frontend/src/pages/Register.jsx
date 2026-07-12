import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

import {
    Button,
    Paper,
    TextField,
    Typography,
} from "@mui/material";

import authService from "../services/authService";
import AuthLayout from "../components/AuthLayout";

function Register() {

    const navigate = useNavigate();

    const [fullName, setFullName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const [loading, setLoading] = useState(false);

    async function handleRegister(e) {

        e.preventDefault();

        if (password !== confirmPassword) {

            alert("Passwords do not match.");

            return;

        }

        try {

            setLoading(true);

            await authService.register({
                fullName,
                email,
                password,
            });

            alert("Registration successful.");

            navigate("/");

        } catch (error) {

            alert(
                error.response?.data?.message ||
                "Registration failed."
            );

        } finally {

            setLoading(false);

        }

    }

    return (

        <AuthLayout>

            <Paper
                elevation={6}
                sx={{
                    width: 430,
                    p: 5,
                    borderRadius: 4,
                }}
            >

                <Typography
                    variant="h4"
                    textAlign="center"
                    fontWeight="bold"
                >
                    Distributed Job Scheduler
                </Typography>

                <Typography
                    textAlign="center"
                    color="text.secondary"
                    mb={4}
                >
                    Create your account
                </Typography>

                <form onSubmit={handleRegister}>

                    <TextField
                        label="Full Name"
                        fullWidth
                        margin="normal"
                        value={fullName}
                        onChange={(e) =>
                            setFullName(e.target.value)
                        }
                    />

                    <TextField
                        label="Email"
                        type="email"
                        fullWidth
                        margin="normal"
                        value={email}
                        onChange={(e) =>
                            setEmail(e.target.value)
                        }
                    />

                    <TextField
                        label="Password"
                        type="password"
                        fullWidth
                        margin="normal"
                        value={password}
                        onChange={(e) =>
                            setPassword(e.target.value)
                        }
                    />

                    <TextField
                        label="Confirm Password"
                        type="password"
                        fullWidth
                        margin="normal"
                        value={confirmPassword}
                        onChange={(e) =>
                            setConfirmPassword(e.target.value)
                        }
                    />

                    <Button
                        variant="contained"
                        fullWidth
                        sx={{
                            mt: 3,
                            py: 1.5,
                        }}
                        type="submit"
                        disabled={loading}
                    >
                        {loading
                            ? "Registering..."
                            : "Register"}
                    </Button>

                </form>

                <Typography
                    textAlign="center"
                    mt={3}
                >
                    Already have an account?{" "}
                    <Link
                        to="/"
                        style={{
                            textDecoration: "none",
                            fontWeight: "bold",
                        }}
                    >
                        Login
                    </Link>
                </Typography>

            </Paper>

        </AuthLayout>

    );

}

export default Register;