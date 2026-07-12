import { useEffect, useState } from "react";
import {
    getProjects,
    createProject,
    deleteProject
} from "../services/projectService";
import { getOrganizations } from "../services/organizationService";

function Projects() {

    const [projects, setProjects] = useState([]);
    const [organizations, setOrganizations] = useState([]);

    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [organizationId, setOrganizationId] = useState("");

    const loadProjects = async () => {
        try {
            const data = await getProjects();
            setProjects(data);
        } catch (error) {
            console.error(error);
        }
    };

    const loadOrganizations = async () => {
        try {
            const data = await getOrganizations();
            setOrganizations(data);

            if (data.length > 0) {
                setOrganizationId(data[0].id);
            }

        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        loadProjects();
        loadOrganizations();
    }, []);

    const handleCreate = async () => {

        if (!name.trim()) return;

        try {

            await createProject({
                organizationId,
                name,
                description
            });

            setName("");
            setDescription("");

            loadProjects();

        } catch (error) {
            alert("Unable to create project.");
        }
    };

    const handleDelete = async (id) => {

        if (!window.confirm("Delete this project?")) return;

        await deleteProject(id);

        loadProjects();
    };

    return (
        <div>

            <h1>Projects</h1>

            <br />

            <select
                value={organizationId}
                onChange={(e) => setOrganizationId(e.target.value)}
            >
                {organizations.map((organization) => (
                    <option
                        key={organization.id}
                        value={organization.id}
                    >
                        {organization.name}
                    </option>
                ))}
            </select>

            <br /><br />

            <input
                placeholder="Project Name"
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
                Create Project
            </button>

            <hr />

            {projects.map((project) => (

                <div
                    key={project.id}
                    style={{
                        border: "1px solid gray",
                        padding: "15px",
                        marginBottom: "10px"
                    }}
                >
                    <h3>{project.name}</h3>

                    <p>{project.description}</p>

                    <small>
                        Organization : {project.organizationName}
                    </small>

                    <br /><br />

                    <button
                        onClick={() => handleDelete(project.id)}
                    >
                        Delete
                    </button>

                </div>

            ))}

        </div>
    );
}

export default Projects;