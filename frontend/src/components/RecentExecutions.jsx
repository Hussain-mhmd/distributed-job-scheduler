import "./RecentExecutions.css";

function RecentExecutions({ executions }) {

    return (

        <div className="dashboard-widget">

            <h2>Recent Executions</h2>

            <table className="dashboard-table">

                <thead>

                    <tr>
                        <th>Job</th>
                        <th>Worker</th>
                        <th>Status</th>
                        <th>Time</th>
                    </tr>

                </thead>

                <tbody>

                    {executions.length === 0 ? (

                        <tr>

                            <td colSpan="4">
                                No execution history.
                            </td>

                        </tr>

                    ) : (

                        executions.slice(0,5).map((execution) => (

                            <tr key={execution.id}>

                                <td>{execution.jobName}</td>

                                <td>{execution.workerName}</td>

                                <td>

                                    <span
                                        className={
                                            execution.executionStatus === "COMPLETED"
                                                ? "status success"
                                                : execution.executionStatus === "FAILED"
                                                ? "status failed"
                                                : "status running"
                                        }
                                    >
                                        {execution.executionStatus}
                                    </span>

                                </td>

                                <td>

                                    {execution.executionTimeMs} ms

                                </td>

                            </tr>

                        ))

                    )}

                </tbody>

            </table>

        </div>

    );

}

export default RecentExecutions;