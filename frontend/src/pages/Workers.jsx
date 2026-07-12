import { useEffect, useState } from "react";
import { getWorkers } from "../services/workerService";

function Workers() {

    const [workers, setWorkers] = useState([]);

    useEffect(() => {

        loadWorkers();

    }, []);

    const loadWorkers = async () => {

        try {

            const data = await getWorkers();

            setWorkers(data);

        } catch (error) {

            console.error(error);

        }

    };

    return (

        <div>

            <h1>Workers</h1>

            <hr />

            {workers.map(worker => (

                <div
                    key={worker.id}
                    style={{
                        border: "1px solid gray",
                        padding: "15px",
                        marginBottom: "10px"
                    }}
                >

                    <h3>{worker.workerName}</h3>

                    <p>Host : {worker.hostName}</p>

                    <p>Status : {worker.workerStatus}</p>

                    <p>Active Jobs : {worker.activeJobs}</p>

                    <p>Heartbeat : {worker.lastHeartbeat}</p>

                </div>

            ))}

        </div>

    );

}

export default Workers;