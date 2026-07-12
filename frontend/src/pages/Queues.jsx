import { useEffect, useState } from "react";
import {
    getQueues,
    createQueue,
    deleteQueue
} from "../services/queueService";
import { getProjects } from "../services/projectService";

function Queues() {

    const [queues, setQueues] = useState([]);
    const [projects, setProjects] = useState([]);

    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [projectId, setProjectId] = useState("");

    const loadQueues = async () => {
        try {
            const data = await getQueues();
            setQueues(data);
        } catch (error) {
            console.error(error);
        }
    };

    const loadProjects = async () => {
        try {
            const data = await getProjects();
            setProjects(data);

            if (data.length > 0) {
                setProjectId(data[0].id);
            }

        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        loadQueues();
        loadProjects();
    }, []);

    const handleCreate = async () => {

        if (!name.trim()) return;

        try {

            await createQueue({
                projectId,
                name,
                description
            });

            setName("");
            setDescription("");

            loadQueues();

        } catch (error) {
            alert("Unable to create queue.");
        }
    };

    const handleDelete = async (id) => {

        if (!window.confirm("Delete this queue?")) return;

        await deleteQueue(id);

        loadQueues();
    };

    return (
        <div>

            <h1>Queues</h1>

            <br />

            <select
                value={projectId}
                onChange={(e) => setProjectId(e.target.value)}
            >
                {projects.map((project) => (
                    <option
                        key={project.id}
                        value={project.id}
                    >
                        {project.name}
                    </option>
                ))}
            </select>

            <br /><br />

            <input
                placeholder="Queue Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
            />

            <br /><br />

            <input
                placeholder="Description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
            />

            <br /><br />

            <button onClick={handleCreate}>
                Create Queue
            </button>

            <hr />

            {queues.map((queue) => (

                <div
                    key={queue.id}
                    style={{
                        border: "1px solid gray",
                        padding: "15px",
                        marginBottom: "10px"
                    }}
                >
                    <h3>{queue.name}</h3>

                    <p>{queue.description}</p>

                    <small>
                        Project : {queue.projectName}
                    </small>

                    <br /><br />

                    <button
                        onClick={() => handleDelete(queue.id)}
                    >
                        Delete
                    </button>

                </div>

            ))}

        </div>
    );
}

export default Queues;