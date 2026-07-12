import { useEffect, useState } from "react";
import { getExecutionHistory } from "../services/executionService";

function ExecutionHistory() {

    const [executions, setExecutions] = useState([]);

    useEffect(() => {

        loadHistory();

    }, []);

    const loadHistory = async () => {

        try {

            const data = await getExecutionHistory();

            setExecutions(data);

        } catch (error) {

            console.error(error);

        }

    };

    return (

        <div>

            <h1>Execution History</h1>

            <hr />

            {executions.map((execution) => (

                <div
                    key={execution.id}
                    style={{
                        border: "1px solid gray",
                        padding: "15px",
                        marginBottom: "10px"
                    }}
                >

                    <h3>{execution.jobName}</h3>

                    <p>Worker : {execution.workerName}</p>

                    <p>Attempt : {execution.attemptNumber}</p>

                    <p>Status : {execution.executionStatus}</p>

                    <p>Execution Time : {execution.executionTimeMs} ms</p>

                    <p>Started : {execution.startedAt}</p>

                    <p>Completed : {execution.completedAt}</p>

                    <p>Error : {execution.errorMessage || "None"}</p>

                </div>

            ))}

        </div>

    );

}

export default ExecutionHistory;