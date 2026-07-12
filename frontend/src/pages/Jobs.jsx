import { useEffect, useState } from "react";
import {
    getJobs,
    createJob,
    deleteJob
} from "../services/jobService";

import { getQueues } from "../services/queueService";

function Jobs() {

    const [jobs, setJobs] = useState([]);
    const [queues, setQueues] = useState([]);

    const [queueId, setQueueId] = useState("");
    const [name, setName] = useState("");
    const [payload, setPayload] = useState("{}");
    const [priority, setPriority] = useState("MEDIUM");
    const [maxRetries, setMaxRetries] = useState(3);
    const [scheduledAt, setScheduledAt] = useState("");

    const loadJobs = async () => {

        try {

            const data = await getJobs();

            setJobs(data);

        } catch (error) {

            console.error(error);

        }

    };

    const loadQueues = async () => {

        try {

            const data = await getQueues();

            setQueues(data);

            if (data.length > 0) {
                setQueueId(data[0].id);
            }

        } catch (error) {

            console.error(error);

        }

    };

    useEffect(() => {

        loadJobs();
        loadQueues();

    }, []);

    const handleCreate = async () => {

        try {

            await createJob({

                queueId,
                name,
                payload,
                priority,
                maxRetries,
                scheduledAt: scheduledAt || null

            });

            setName("");
            setPayload("{}");
            setPriority("MEDIUM");
            setMaxRetries(3);
            setScheduledAt("");

            loadJobs();

        } catch (error) {

            console.error(error);

            alert(error.response?.data?.message || "Unable to create job.");

        }

    };

    const handleDelete = async (id) => {

        if (!window.confirm("Delete this job?")) return;

        await deleteJob(id);

        loadJobs();

    };

    return (

        <div>

            <h1>Jobs</h1>

            <br />

            <select
                value={queueId}
                onChange={(e) => setQueueId(e.target.value)}
            >

                {queues.map((queue) => (

                    <option
                        key={queue.id}
                        value={queue.id}
                    >
                        {queue.name}
                    </option>

                ))}

            </select>

            <br /><br />

            <input
                placeholder="Job Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
            />

            <br /><br />

            <select
                value={priority}
                onChange={(e) => setPriority(e.target.value)}
            >
                <option value="LOW">LOW</option>
                <option value="MEDIUM">MEDIUM</option>
                <option value="HIGH">HIGH</option>
            </select>

            <br /><br />

            <input
                type="number"
                value={maxRetries}
                onChange={(e) => setMaxRetries(Number(e.target.value))}
            />

            <br /><br />

            <input
                type="datetime-local"
                value={scheduledAt}
                onChange={(e) => setScheduledAt(e.target.value)}
            />

            <br /><br />

            <textarea
                rows="5"
                cols="40"
                value={payload}
                onChange={(e) => setPayload(e.target.value)}
            />

            <br /><br />

            <button onClick={handleCreate}>
                Create Job
            </button>

            <hr />

            {jobs.map((job) => (

                <div
                    key={job.id}
                    style={{
                        border: "1px solid gray",
                        padding: "15px",
                        marginBottom: "10px"
                    }}
                >

                    <h3>{job.name}</h3>

                    <p>Queue : {job.queueName}</p>

                    <p>Status : {job.status}</p>

                    <p>Priority : {job.priority}</p>

                    <p>
                        Retries : {job.retryCount} / {job.maxRetries}
                    </p>

                    <p>
                        Scheduled : {job.scheduledAt || "Immediate"}
                    </p>

                    <button
                        onClick={() => handleDelete(job.id)}
                    >
                        Delete
                    </button>

                </div>

            ))}

        </div>

    );

}

export default Jobs;