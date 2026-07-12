import { useEffect, useState } from "react";

import {
    Box,
    Typography,
    Button,
    Card,
    CardContent,
    CardActions,
    TextField,
    MenuItem,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Grid
} from "@mui/material";

import WorkIcon from "@mui/icons-material/Work";
import VisibilityIcon from "@mui/icons-material/Visibility";

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
    const [viewOpen, setViewOpen] = useState(false);
const [selectedJob, setSelectedJob] = useState(null);

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

            <Box
    display="flex"
    justifyContent="space-between"
    alignItems="center"
    mb={4}
>

    <Box>

        <Typography
            variant="h4"
            fontWeight="bold"
        >
            Jobs
        </Typography>

        <Typography color="text.secondary">
            Manage and monitor scheduled jobs.
        </Typography>

    </Box>

</Box>

            <br />

            <TextField
    select
    fullWidth
    label="Queue"
    margin="normal"
    value={queueId}
    onChange={(e) => setQueueId(e.target.value)}
>

    {queues.map((queue) => (

        <MenuItem
            key={queue.id}
            value={queue.id}
        >
            {queue.name}
        </MenuItem>

    ))}

</TextField>

            <br /><br />

            <TextField
    fullWidth
    label="Job Name"
    margin="normal"
    value={name}
    onChange={(e) => setName(e.target.value)}
/>

            <br /><br />

            <TextField
    select
    fullWidth
    label="Priority"
    margin="normal"
    value={priority}
    onChange={(e) => setPriority(e.target.value)}
>

    <MenuItem value="LOW">LOW</MenuItem>
    <MenuItem value="MEDIUM">MEDIUM</MenuItem>
    <MenuItem value="HIGH">HIGH</MenuItem>

</TextField>

            <br /><br />

            <TextField
    fullWidth
    type="number"
    label="Max Retries"
    margin="normal"
    value={maxRetries}
    onChange={(e) =>
        setMaxRetries(Number(e.target.value))
    }
/>

            <br /><br />

            <TextField
    fullWidth
    type="datetime-local"
    margin="normal"
    value={scheduledAt}
    onChange={(e) =>
        setScheduledAt(e.target.value)
    }
    InputLabelProps={{
        shrink: true
    }}
/>

            <br /><br />

            <TextField
    fullWidth
    multiline
    rows={5}
    label="Payload"
    margin="normal"
    value={payload}
    onChange={(e) =>
        setPayload(e.target.value)
    }
/>

            <br /><br />

            <button onClick={handleCreate}>
                Create Job
            </button>

            <hr />

            {jobs.map((job) => (

                <Card
    sx={{
        mb: 2,
        borderRadius: 3
    }}
>

    <CardContent>

        <Box
            display="flex"
            alignItems="center"
            gap={1}
        >

            <WorkIcon color="primary"/>

            <Typography
                variant="h6"
                fontWeight="bold"
            >
                {job.name}
            </Typography>

        </Box>

        <Typography mt={2}>
            <b>Queue:</b> {job.queueName}
        </Typography>

        <Typography>
            <b>Status:</b> {job.status}
        </Typography>

        <Typography>
            <b>Priority:</b> {job.priority}
        </Typography>

        <Typography>
            <b>Retries:</b> {job.retryCount} / {job.maxRetries}
        </Typography>

    </CardContent>

    <CardActions>

        <Button
            startIcon={<VisibilityIcon/>}
            onClick={() => {

                setSelectedJob(job);

                setViewOpen(true);

            }}
        >
            View
        </Button>

        <Button
            color="error"
            onClick={() =>
                handleDelete(job.id)
            }
        >
            Delete
        </Button>

    </CardActions>

</Card>

            ))}
            <Dialog
    open={viewOpen}
    onClose={() => setViewOpen(false)}
    fullWidth
    maxWidth="sm"
>

    <DialogTitle>
        Job Details
    </DialogTitle>

    <DialogContent>

        {selectedJob && (

            <Box mt={1}>

                <Typography><b>Name:</b> {selectedJob.name}</Typography>

                <Typography><b>Queue:</b> {selectedJob.queueName}</Typography>

                <Typography><b>Status:</b> {selectedJob.status}</Typography>

                <Typography><b>Priority:</b> {selectedJob.priority}</Typography>

                <Typography><b>Retries:</b> {selectedJob.retryCount} / {selectedJob.maxRetries}</Typography>

                <Typography mt={2}>
                    <b>Payload:</b>
                </Typography>

                <TextField
                    fullWidth
                    multiline
                    rows={6}
                    value={selectedJob.payload}
                    InputProps={{
                        readOnly: true
                    }}
                    margin="normal"
                />

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

        </div>

    );

}

export default Jobs;