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

import LayersIcon from "@mui/icons-material/Layers";
import AddIcon from "@mui/icons-material/Add";
import VisibilityIcon from "@mui/icons-material/Visibility";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

import {
    getQueues,
    createQueue,
    updateQueue,
    deleteQueue
} from "../services/queueService";

import { getProjects } from "../services/projectService";

function Queues() {

    const [queues, setQueues] = useState([]);
    const [projects, setProjects] = useState([]);

    const [selectedQueue, setSelectedQueue] = useState(null);

    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [projectId, setProjectId] = useState("");

    const [createOpen, setCreateOpen] = useState(false);
    const [viewOpen, setViewOpen] = useState(false);
    const [editOpen, setEditOpen] = useState(false);

    useEffect(() => {

        loadQueues();
        loadProjects();

    }, []);

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

    const handleCreate = async () => {

        if (!name.trim()) return;

        try {

            await createQueue({

                projectId,
                name,
                description

            });

            setCreateOpen(false);

            setName("");
            setDescription("");

            loadQueues();

        } catch {

            alert("Unable to create queue.");

        }

    };

    const handleUpdate = async () => {

        try {

            await updateQueue(

                selectedQueue.id,

                {

                    projectId,
                    name,
                    description

                }

            );

            setEditOpen(false);

            loadQueues();

        } catch {

            alert("Unable to update queue.");

        }

    };

    const handleDelete = async (id) => {

        if (!window.confirm("Delete this queue?")) return;

        await deleteQueue(id);

        loadQueues();

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

Queues

</Typography>

<Typography color="text.secondary">

Manage all your queues.

</Typography>

</Box>

<Button
variant="contained"
startIcon={<AddIcon />}
onClick={() => {

setName("");
setDescription("");

if(projects.length>0)
setProjectId(projects[0].id);

setCreateOpen(true);

}}
>

Create Queue

</Button>

</Box>

<Grid container spacing={3}>

{queues.map((queue)=>(

<Grid

item

xs={12}

sm={6}

md={4}

key={queue.id}

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

<LayersIcon color="primary"/>

<Typography

variant="h6"

fontWeight="bold"

>

{queue.name}

</Typography>

</Box>

<Typography

color="text.secondary"

sx={{minHeight:45}}

>

{queue.description || "No Description"}

</Typography>

<Typography

variant="body2"

mt={2}

>

<b>Project:</b> {queue.projectName}

</Typography>

<Typography

variant="caption"

display="block"

mt={2}

color="text.secondary"

>

Created on{" "}

{new Date(

queue.createdAt

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

setSelectedQueue(queue);

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

setSelectedQueue(queue);

setName(queue.name);

setDescription(queue.description);

setProjectId(queue.projectId);

setEditOpen(true);

}}

>

Edit

</Button>

<Button

size="small"

color="error"

startIcon={<DeleteIcon/>}

onClick={()=>handleDelete(queue.id)}

>

Delete

</Button>

</CardActions>

</Card>

</Grid>

))}

</Grid>
{/* -------------------- Create Queue -------------------- */}

<Dialog
    open={createOpen}
    onClose={() => setCreateOpen(false)}
    fullWidth
    maxWidth="sm"
>

    <DialogTitle>
        Create Queue
    </DialogTitle>

    <DialogContent>

        <TextField
            select
            fullWidth
            label="Project"
            margin="normal"
            value={projectId}
            onChange={(e) => setProjectId(e.target.value)}
        >

            {projects.map((project) => (

                <MenuItem
                    key={project.id}
                    value={project.id}
                >
                    {project.name}
                </MenuItem>

            ))}

        </TextField>

        <TextField
            fullWidth
            label="Queue Name"
            margin="normal"
            value={name}
            onChange={(e) => setName(e.target.value)}
        />

        <TextField
            fullWidth
            multiline
            rows={3}
            label="Description"
            margin="normal"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
        />

    </DialogContent>

    <DialogActions>

        <Button onClick={() => setCreateOpen(false)}>
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

{/* -------------------- View Queue -------------------- */}

<Dialog
    open={viewOpen}
    onClose={() => setViewOpen(false)}
    fullWidth
    maxWidth="sm"
>

    <DialogTitle>
        Queue Details
    </DialogTitle>

    <DialogContent>

        {selectedQueue && (

            <Box mt={1}>

                <Typography gutterBottom>
                    <b>Name :</b> {selectedQueue.name}
                </Typography>

                <Typography gutterBottom>
                    <b>Description :</b> {selectedQueue.description}
                </Typography>

                <Typography gutterBottom>
                    <b>Project :</b> {selectedQueue.projectName}
                </Typography>

                <Typography color="text.secondary">

                    Created on{" "}

                    {new Date(
                        selectedQueue.createdAt
                    ).toLocaleDateString()}

                </Typography>

            </Box>

        )}

    </DialogContent>

    <DialogActions>

        <Button onClick={() => setViewOpen(false)}>
            Close
        </Button>

    </DialogActions>

</Dialog>

{/* -------------------- Edit Queue -------------------- */}

<Dialog
    open={editOpen}
    onClose={() => setEditOpen(false)}
    fullWidth
    maxWidth="sm"
>

    <DialogTitle>
        Edit Queue
    </DialogTitle>

    <DialogContent>

        <TextField
            select
            fullWidth
            label="Project"
            margin="normal"
            value={projectId}
            onChange={(e) => setProjectId(e.target.value)}
        >

            {projects.map((project) => (

                <MenuItem
                    key={project.id}
                    value={project.id}
                >
                    {project.name}
                </MenuItem>

            ))}

        </TextField>

        <TextField
            fullWidth
            label="Queue Name"
            margin="normal"
            value={name}
            onChange={(e) => setName(e.target.value)}
        />

        <TextField
            fullWidth
            multiline
            rows={3}
            label="Description"
            margin="normal"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
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

export default Queues;