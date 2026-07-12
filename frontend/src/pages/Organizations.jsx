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
    Stack
} from "@mui/material";

import AddIcon from "@mui/icons-material/Add";
import BusinessIcon from "@mui/icons-material/Business";
import VisibilityIcon from "@mui/icons-material/Visibility";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

import {
    getOrganizations,
    createOrganization,
    updateOrganization,
    deleteOrganization
} from "../services/organizationService";

function Organizations() {

    const [organizations, setOrganizations] = useState([]);

    const [name, setName] = useState("");
    const [description, setDescription] = useState("");

    const [selectedOrganization, setSelectedOrganization] = useState(null);

    const [createOpen, setCreateOpen] = useState(false);
    const [viewOpen, setViewOpen] = useState(false);
    const [editOpen, setEditOpen] = useState(false);

    useEffect(() => {
        loadOrganizations();
    }, []);

    async function loadOrganizations() {

        try {

            const data = await getOrganizations();

            setOrganizations(data);

        } catch (error) {

            console.error(error);

        }

    }

    async function handleCreate() {

        if (!name.trim()) {

            alert("Organization name is required.");

            return;

        }

        try {

            await createOrganization({

                name,
                description

            });

            setCreateOpen(false);

            setName("");
            setDescription("");

            loadOrganizations();

        } catch {

            alert("Unable to create organization.");

        }

    }

    async function handleUpdate() {

        try {

            await updateOrganization(

                selectedOrganization.id,

                {
                    name,
                    description
                }

            );

            setEditOpen(false);

            loadOrganizations();

        } catch {

            alert("Unable to update organization.");

        }

    }

    async function handleDelete(id) {

        if (!window.confirm("Delete this organization?")) {

            return;

        }

        try {

            await deleteOrganization(id);

            loadOrganizations();

        } catch {

            alert("Unable to delete organization.");

        }

    }

    return (

    <Box
    display="flex"
    justifyContent="space-between"
    alignItems="flex-start"
    flexWrap="wrap"
    mb={4}
>

    <Box>

        <Typography
            variant="h4"
            fontWeight="bold"
        >
            Organizations
        </Typography>

        <Typography color="text.secondary">
            Manage all your organizations.
        </Typography>

    </Box>

    <Button
        variant="contained"
        startIcon={<AddIcon />}
        onClick={() => {

            setName("");
            setDescription("");

            setCreateOpen(true);

        }}
        sx={{
            borderRadius: 2,
            textTransform: "none",
            px: 3,
            py: 1
        }}
    >
        Create Organization
    </Button>

        <Grid
            container
            spacing={3}
        >

            {organizations.length === 0 && (

                <Grid item xs={12}>

                    <Card
                        sx={{
                            textAlign: "center",
                            py: 6
                        }}
                    >

                        <BusinessIcon
    sx={{
        color:"#1976d2",
        fontSize:30
    }}
/>

                        <Typography
                            variant="h6"
                            mt={2}
                        >
                            No Organizations Found
                        </Typography>

                        <Typography
                            color="text.secondary"
                        >
                            Create your first organization.
                        </Typography>

                    </Card>

                </Grid>

            )}

            {organizations.map((organization) => (

                <Grid
                    item
                    xs={12}
                    md={6}
                    lg={4}
                    key={organization.id}
                >

                    <Card
    elevation={4}
    sx={{
        height: "100%",
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-between",
        borderRadius: 3,
        transition: "0.3s",

        "&:hover": {
            transform: "translateY(-6px)",
            boxShadow: 8
        }
    }}
>

                        <CardContent>

                            <Box
    display="flex"
    alignItems="center"
    gap={1}
>

    <BusinessIcon
    sx={{
        color:"#1976d2",
        fontSize:30
    }}
/>

    <Typography
        variant="h6"
        fontWeight="bold"
    >
        {organization.name}
    </Typography>

</Box>

                            <Typography
    color="text.secondary"
    sx={{
        mt:1,
        minHeight:45
    }}
>
    {organization.description || "No description"}
</Typography>

                            <Typography
    variant="caption"
    color="text.secondary"
>
    Created

    <Typography
        component="span"
        fontWeight="bold"
    >

        {" "}
        {new Date(
            organization.createdAt
        ).toLocaleDateString(
            "en-US",
            {
                day:"2-digit",
                month:"short",
                year:"numeric"
            }
        )}

    </Typography>

</Typography>

                        </CardContent>

                        <CardActions
    sx={{
        justifyContent: "space-between",
        px:2,
        pb:2
    }}
>

                            <Button

    color="primary"

    startIcon={<VisibilityIcon />}

    size="small"

    onClick={() => {

        setSelectedOrganization(organization);

        setViewOpen(true);

    }}

>

    View

</Button>

                            <Button

    color="warning"

    startIcon={<EditIcon />}

    size="small"

    onClick={() => {

        setSelectedOrganization(organization);

        setName(organization.name);

        setDescription(organization.description);

        setEditOpen(true);

    }}

>

    Edit

</Button>

                            <Button

    color="error"

    startIcon={<DeleteIcon />}

    size="small"

    onClick={() =>
        handleDelete(
            organization.id
        )
    }

>

    Delete

</Button>

                        </CardActions>

                    </Card>

                </Grid>

            ))}

        </Grid>
                {/* Create Organization Dialog */}

        <Dialog
            open={createOpen}
            onClose={() => setCreateOpen(false)}
            fullWidth
            maxWidth="sm"
        >

            <DialogTitle>

<Box
display="flex"
alignItems="center"
gap={1}
>

<BusinessIcon
    sx={{
        color:"#1976d2",
        fontSize:30
    }}
/>

Create Organization

</Box>

</DialogTitle>

            <DialogContent>

                <TextField
                    label="Organization Name"
                    fullWidth
                    margin="normal"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />

                <TextField
                    label="Description"
                    fullWidth
                    multiline
                    rows={4}
                    margin="normal"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
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

        {/* View Dialog */}

        <Dialog
            open={viewOpen}
            onClose={() => setViewOpen(false)}
            fullWidth
            maxWidth="sm"
        >

            <DialogTitle>
                Organization Details
            </DialogTitle>

            <DialogContent>

                {selectedOrganization && (

                    <Stack spacing={2} mt={1}>

                        <TextField
                            label="Name"
                            value={selectedOrganization.name}
                            InputProps={{
                                readOnly: true
                            }}
                        />

                        <TextField
                            label="Description"
                            value={
                                selectedOrganization.description || "-"
                            }
                            multiline
                            rows={4}
                            InputProps={{
                                readOnly: true
                            }}
                        />

                        <TextField
                            label="Created At"
                            value={
                                selectedOrganization.createdAt
                                    ?.replace("T", " ")
                                    .substring(0, 19)
                            }
                            InputProps={{
                                readOnly: true
                            }}
                        />

                    </Stack>

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

        {/* Edit Dialog */}

        <Dialog
            open={editOpen}
            onClose={() => setEditOpen(false)}
            fullWidth
            maxWidth="sm"
        >

            <DialogTitle>
                Edit Organization
            </DialogTitle>

            <DialogContent>

                <TextField
                    label="Organization Name"
                    fullWidth
                    margin="normal"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />

                <TextField
                    label="Description"
                    fullWidth
                    multiline
                    rows={4}
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

export default Organizations;