import { Box, Typography } from "@mui/material";

function AuthLayout({ children }) {

    return (

        <Box
            sx={{
                minHeight: "100vh",
                display: "grid",
                gridTemplateColumns: {
                    xs: "1fr",
                    md: "1.2fr 1fr"
                }
            }}
        >

            <Box
                sx={{
                    display: {
                        xs: "none",
                        md: "flex"
                    },
                    flexDirection: "column",
                    justifyContent: "center",
                    px: 8,
                    background:
                        "linear-gradient(135deg,#2563eb,#1e40af)",
                    color: "white"
                }}
            >

                <Typography
                    variant="h3"
                    fontWeight="bold"
                    mb={3}
                >
                    ⚡ Distributed Job Scheduler
                </Typography>

                <Typography
                    variant="h6"
                    sx={{
                        opacity: .9,
                        mb: 5
                    }}
                >
                    Enterprise-grade platform for
                    distributed task execution.
                </Typography>

                <Typography mb={2}>
                    ✅ Worker Pool
                </Typography>

                <Typography mb={2}>
                    ✅ Retry Engine
                </Typography>

                <Typography mb={2}>
                    ✅ Dead Letter Queue
                </Typography>

                <Typography mb={2}>
                    ✅ Metrics Dashboard
                </Typography>

                <Typography mb={2}>
                    ✅ Execution History
                </Typography>

            </Box>

            <Box
                sx={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    background: "#f5f7fb",
                    p: 4
                }}
            >

                {children}

            </Box>

        </Box>

    );

}

export default AuthLayout;