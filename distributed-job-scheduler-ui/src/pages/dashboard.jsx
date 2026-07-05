import { useEffect, useState } from "react";
import api from "../api/axios";

function Dashboard() {

    const [metrics,setMetrics]=useState({});
    const [organizations,setOrganizations]=useState([]);
    const [projects,setProjects]=useState([]);
    const [queues,setQueues]=useState([]);
    const [jobs,setJobs]=useState([]);
    const [deadLetters,setDeadLetters]=useState([]);

    useEffect(()=>{

        load();

    },[]);

    async function load(){

        const m=await api.get("/metrics");
        setMetrics(m.data);

        const o=await api.get("/organizations");
        setOrganizations(o.data.data);

        const p=await api.get("/projects");
        setProjects(p.data.data);

        const q=await api.get("/queues");
        setQueues(q.data.data);

        const j=await api.get("/jobs");
        setJobs(j.data.data);

        const d=await api.get("/dead-letter");
        setDeadLetters(d.data);

    }

    return(

        <div style={{padding:"30px"}}>

            <h1>Dashboard</h1>

            <button onClick={()=>{
                localStorage.clear();
                window.location.reload();
            }}>Logout</button>

            <hr/>

            <h2>Metrics</h2>

            <p>Total Jobs : {metrics.totalJobs}</p>
            <p>Pending : {metrics.pendingJobs}</p>
            <p>Completed : {metrics.completedJobs}</p>
            <p>Failed : {metrics.failedJobs}</p>
            <p>Dead Letter : {metrics.deadLetterJobs}</p>

            <hr/>

            <h2>Organizations</h2>

            <ul>

                {organizations.map(o=>

                    <li key={o.id}>{o.name}</li>

                )}

            </ul>

            <hr/>

            <h2>Projects</h2>

            <ul>

                {projects.map(p=>

                    <li key={p.id}>{p.name}</li>

                )}

            </ul>

            <hr/>

            <h2>Queues</h2>

            <ul>

                {queues.map(q=>

                    <li key={q.id}>{q.name}</li>

                )}

            </ul>

            <hr/>

            <h2>Jobs</h2>

            <ul>

                {jobs.map(j=>

                    <li key={j.id}>
                        {j.name} - {j.status}
                    </li>

                )}

            </ul>

            <hr/>

            <h2>Dead Letter Queue</h2>

            <ul>

                {deadLetters.map(d=>

                    <li key={d.id}>
                        {d.jobName}
                    </li>

                )}

            </ul>

        </div>

    );

}

export default Dashboard;