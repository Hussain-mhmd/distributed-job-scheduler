import "./WorkerStatus.css";

function WorkerStatus({ workers }) {

    return (

        <div className="dashboard-widget">

            <h2>Active Workers</h2>

            {workers.length === 0 ? (

                <p>No workers available.</p>

            ) : (

                workers.map(worker => (

                    <div
                        key={worker.id}
                        className="worker-card"
                    >

                        <div>

                            <h4>{worker.workerName}</h4>

                            <p>{worker.hostName}</p>

                        </div>

                        <div>

                            <span
                                className={
                                    worker.workerStatus === "ACTIVE"
                                        ? "status success"
                                        : "status failed"
                                }
                            >
                                {worker.workerStatus}
                            </span>

                            <p>

                                Active Jobs : {worker.activeJobs}

                            </p>

                        </div>

                    </div>

                ))

            )}

        </div>

    );

}

export default WorkerStatus;