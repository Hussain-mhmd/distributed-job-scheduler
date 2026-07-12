import { useEffect, useState } from "react";
import { getDeadLetterJobs } from "../services/deadLetterService";

function DeadLetter() {

    const [jobs, setJobs] = useState([]);

    useEffect(() => {
        loadDeadLetterJobs();
    }, []);

    const loadDeadLetterJobs = async () => {

        try {

            const data = await getDeadLetterJobs();

            setJobs(data);

        } catch (error) {

            console.error(error);

        }

    };

    return (

        <div>

            <h1>Dead Letter Queue</h1>

            <hr />

            {jobs.length === 0 && (
                <p>No failed jobs.</p>
            )}

            {jobs.map(job => (

                <div
                    key={job.id}
                    style={{
                        border: "1px solid gray",
                        padding: "15px",
                        marginBottom: "10px"
                    }}
                >

                    <h3>{job.jobName}</h3>

                    <p>Failure Reason : {job.failureReason}</p>

                    <p>Failed At : {job.failedAt}</p>

                    <p>Created At : {job.createdAt}</p>

                </div>

            ))}

        </div>

    );

}

export default DeadLetter;