import { useEffect, useState } from "react";

import MetricCard from "../components/MetricCard";
import RecentExecutions from "../components/RecentExecutions";

import metricsService from "../services/metricsService";
import { getExecutionHistory } from "../services/executionService";

import "../styles/dashboard.css";
import WorkerStatus from "../components/WorkerStatus";
import { getWorkers } from "../services/workerService";

function Dashboard() {

    const [metrics, setMetrics] = useState(null);
    const [executions, setExecutions] = useState([]);
    const [workers, setWorkers] = useState([]);

    useEffect(() => {

        loadMetrics();
        loadExecutions();
        loadWorkers();

    }, []);

    async function loadMetrics() {

        try {

            const response = await metricsService.getMetrics();

            setMetrics(response.data);

        } catch (error) {

            console.error(error);

        }

    }

    async function loadExecutions() {

        try {

            const data = await getExecutionHistory();

            setExecutions(data);

        } catch (error) {

            console.error(error);

        }

    }
    async function loadWorkers() {

    try {

        const data = await getWorkers();

        setWorkers(data);

    } catch (error) {

        console.error(error);

    }

}

    if (!metrics) {

        return <h2>Loading Dashboard...</h2>;

    }

    return (

        <div>

            <div style={{ marginBottom: "30px" }}>

                <h1 style={{ fontSize: "36px" }}>
                    Dashboard
                </h1>

                <p
                    style={{
                        color: "#6b7280",
                        marginTop: "8px"
                    }}
                >
                    Monitor your distributed job scheduler in real time.
                </p>

            </div>

            <div className="dashboard-grid">

                <MetricCard
                    title="Organizations"
                    value={metrics.totalOrganizations}
                    color="#2563eb"
                />

                <MetricCard
                    title="Projects"
                    value={metrics.totalProjects}
                    color="#16a34a"
                />

                <MetricCard
                    title="Queues"
                    value={metrics.totalQueues}
                    color="#9333ea"
                />

                <MetricCard
                    title="Jobs"
                    value={metrics.totalJobs}
                    color="#ea580c"
                />

                <MetricCard
                    title="Completed"
                    value={metrics.completedJobs}
                    color="#16a34a"
                />

                <MetricCard
                    title="Failed"
                    value={metrics.failedJobs}
                    color="#dc2626"
                />

                <MetricCard
                    title="Pending"
                    value={metrics.pendingJobs}
                    color="#d97706"
                />

                <MetricCard
                    title="Running"
                    value={metrics.runningJobs}
                    color="#2563eb"
                />

                <MetricCard
                    title="Success Rate"
                    value={`${metrics.successRate.toFixed(2)} %`}
                    color="#14b8a6"
                />

            </div>

            <div className="dashboard-bottom">

    <div className="dashboard-left">

        <RecentExecutions executions={executions} />

    </div>

    <div className="dashboard-right">

        <WorkerStatus workers={workers} />

    </div>

</div>

        </div>

    );

}

export default Dashboard;