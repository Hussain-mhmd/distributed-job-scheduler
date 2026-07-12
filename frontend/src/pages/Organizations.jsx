import { useEffect, useState } from "react";
import {
    getOrganizations,
    createOrganization,
    deleteOrganization
} from "../services/organizationService";

function Organizations() {

    const [organizations, setOrganizations] = useState([]);
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");

    const loadOrganizations = async () => {
        try {
            const data = await getOrganizations();
            setOrganizations(data);
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        loadOrganizations();
    }, []);

    const handleCreate = async () => {

        if (!name.trim()) return;

        try {

            await createOrganization({
                name,
                description
            });

            setName("");
            setDescription("");

            loadOrganizations();

        } catch (error) {
            alert("Unable to create organization.");
        }
    };

    const handleDelete = async (id) => {

        if (!window.confirm("Delete this organization?")) return;

        await deleteOrganization(id);

        loadOrganizations();
    };

    return (
        <div>

            <h1>Organizations</h1>

            <br />

            <input
                placeholder="Organization Name"
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
                Create Organization
            </button>

            <hr />

            {organizations.map((organization) => (

                <div
                    key={organization.id}
                    style={{
                        border: "1px solid gray",
                        padding: "15px",
                        marginBottom: "10px"
                    }}
                >
                    <h3>{organization.name}</h3>

                    <p>{organization.description}</p>

                    <button
                        onClick={() => handleDelete(organization.id)}
                    >
                        Delete
                    </button>

                </div>

            ))}

        </div>
    );
}

export default Organizations;