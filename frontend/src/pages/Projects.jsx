import { useEffect, useState } from "react";

import {
    Box,
    Typography,
    Button,
    Card,
    CardContent,
    CardActions,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    TextField,
    Grid,
    MenuItem,
    Divider
} from "@mui/material";

import FolderIcon from "@mui/icons-material/Folder";
import AddIcon from "@mui/icons-material/Add";
import VisibilityIcon from "@mui/icons-material/Visibility";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

import {
    getProjects,
    createProject,
    updateProject,
    deleteProject
} from "../services/projectService";

import {
    getOrganizations
} from "../services/organizationService";

function Projects() {

    const [projects, setProjects] = useState([]);
    const [organizations, setOrganizations] = useState([]);

    const [selectedProject, setSelectedProject] = useState(null);

    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [organizationId, setOrganizationId] = useState("");

    const [createOpen, setCreateOpen] = useState(false);
    const [viewOpen, setViewOpen] = useState(false);
    const [editOpen, setEditOpen] = useState(false);

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

            setCreateOpen(false);

            setName("");

            setDescription("");

            loadProjects();

        } catch (error) {

            alert("Unable to create project.");

        }

    };

    const handleUpdate = async () => {

        try {

            await updateProject(

                selectedProject.id,

                {

                    organizationId,

                    name,

                    description

                }

            );

            setEditOpen(false);

            loadProjects();

        }

        catch {

            alert("Unable to update project.");

        }

    };

    const handleDelete = async (id) => {

        if (!window.confirm("Delete this project?")) return;

        await deleteProject(id);

        loadProjects();

    };

    return (

<Box>

<Box

display="flex"

justifyContent="space-between"

alignItems="flex-start"

mb={4}

>

<Box>

<Typography

variant="h4"

fontWeight="bold"

>

Projects

</Typography>

<Typography color="text.secondary">

Manage all your projects.

</Typography>

</Box>

<Button

variant="contained"

startIcon={<AddIcon />}

onClick={() => {

setName("");

setDescription("");

if (organizations.length > 0)

setOrganizationId(organizations[0].id);

setCreateOpen(true);

}}

>

Create Project

</Button>

</Box>

<Grid container spacing={3}>

{projects.map((project) => (

<Grid

item

xs={12}

sm={6}

md={4}

key={project.id}

>

<Card

elevation={4}

sx={{

height:"100%",

display:"flex",

flexDirection:"column",

justifyContent:"space-between",

borderRadius:3,

transition:"0.3s",

"&:hover":{

transform:"translateY(-4px)",

boxShadow:8

}

}}

>

<CardContent>

<Box

display="flex"

alignItems="center"

gap={1}

mb={1}

>

<FolderIcon

color="primary"

/>

<Typography

variant="h6"

fontWeight="bold"

>

{project.name}

</Typography>

</Box>

<Typography

color="text.secondary"

sx={{

minHeight:45

}}

>

{project.description || "No Description"}

</Typography>

<Typography

variant="body2"

sx={{

mt:2

}}

>

<b>Organization:</b>{" "}

{project.organizationName}

</Typography>

<Typography

variant="caption"

color="text.secondary"

display="block"

mt={2}

>

Created on{" "}

{new Date(

project.createdAt

).toLocaleDateString(

"en-US",

{

day:"2-digit",

month:"short",

year:"numeric"

}

)}

</Typography>

</CardContent>

<Divider/>

<CardActions

sx={{

justifyContent:"space-between"

}}

>

<Button

size="small"

startIcon={<VisibilityIcon/>}

onClick={()=>{

setSelectedProject(project);

setViewOpen(true);

}}

>

View

</Button>

<Button

size="small"

color="warning"

startIcon={<EditIcon/>}

onClick={()=>{

setSelectedProject(project);

setName(project.name);

setDescription(project.description);

setOrganizationId(project.organizationId);

setEditOpen(true);

}}

>

Edit

</Button>

<Button

size="small"

color="error"

startIcon={<DeleteIcon/>}

onClick={()=>handleDelete(project.id)}

>

Delete

</Button>

</CardActions>

</Card>

</Grid>

))}

</Grid>
{/* -------------------- Create Project -------------------- */}

<Dialog
    open={createOpen}
    onClose={() => setCreateOpen(false)}
    fullWidth
    maxWidth="sm"
>

    <DialogTitle>
        Create Project
    </DialogTitle>

    <DialogContent>

        <TextField
            select
            fullWidth
            label="Organization"
            margin="normal"
            value={organizationId}
            onChange={(e) =>
                setOrganizationId(e.target.value)
            }
        >

            {organizations.map((organization) => (

                <MenuItem
                    key={organization.id}
                    value={organization.id}
                >
                    {organization.name}
                </MenuItem>

            ))}

        </TextField>

        <TextField
            fullWidth
            label="Project Name"
            margin="normal"
            value={name}
            onChange={(e) =>
                setName(e.target.value)
            }
        />

        <TextField
            fullWidth
            multiline
            rows={3}
            label="Description"
            margin="normal"
            value={description}
            onChange={(e) =>
                setDescription(e.target.value)
            }
        />

    </DialogContent>

    <DialogActions>

        <Button
            onClick={() => setCreateOpen(false)}
        >
            Cancel
        </Button>

        <Button
            variant="contained"
            onClick={handleCreate}
        >
            Create
        </Button>

    </DialogActions>

</Dialog>


{/* -------------------- View Project -------------------- */}

<Dialog
    open={viewOpen}
    onClose={() => setViewOpen(false)}
    fullWidth
    maxWidth="sm"
>

    <DialogTitle>

        Project Details

    </DialogTitle>

    <DialogContent>

        {selectedProject && (

            <Box mt={1}>

                <Typography gutterBottom>

                    <b>Name :</b>{" "}
                    {selectedProject.name}

                </Typography>

                <Typography gutterBottom>

                    <b>Description :</b>{" "}
                    {selectedProject.description}

                </Typography>

                <Typography gutterBottom>

                    <b>Organization :</b>{" "}
                    {selectedProject.organizationName}

                </Typography>

                <Typography
                    color="text.secondary"
                >

                    Created on{" "}

                    {new Date(

                        selectedProject.createdAt

                    ).toLocaleDateString()}

                </Typography>

            </Box>

        )}

    </DialogContent>

    <DialogActions>

        <Button
            onClick={() => setViewOpen(false)}
        >
            Close
        </Button>

    </DialogActions>

</Dialog>


{/* -------------------- Edit Project -------------------- */}

<Dialog
    open={editOpen}
    onClose={() => setEditOpen(false)}
    fullWidth
    maxWidth="sm"
>

    <DialogTitle>

        Edit Project

    </DialogTitle>

    <DialogContent>

        <TextField
            select
            fullWidth
            label="Organization"
            margin="normal"
            value={organizationId}
            onChange={(e) =>
                setOrganizationId(e.target.value)
            }
        >

            {organizations.map((organization) => (

                <MenuItem
                    key={organization.id}
                    value={organization.id}
                >
                    {organization.name}
                </MenuItem>

            ))}

        </TextField>

        <TextField
            fullWidth
            label="Project Name"
            margin="normal"
            value={name}
            onChange={(e) =>
                setName(e.target.value)
            }
        />

        <TextField
            fullWidth
            multiline
            rows={3}
            label="Description"
            margin="normal"
            value={description}
            onChange={(e) =>
                setDescription(e.target.value)
            }
        />

    </DialogContent>

    <DialogActions>

        <Button
            onClick={() => setEditOpen(false)}
        >
            Cancel
        </Button>

        <Button
            variant="contained"
            onClick={handleUpdate}
        >
            Update
        </Button>

    </DialogActions>

</Dialog>

</Box>

);

}

export default Projects;