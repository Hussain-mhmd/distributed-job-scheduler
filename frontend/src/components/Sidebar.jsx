import { NavLink } from "react-router-dom";
import {
    FaTachometerAlt,
    FaBuilding,
    FaFolder,
    FaLayerGroup,
    FaBriefcase,
    FaServer,
    FaHistory,
    FaExclamationTriangle
} from "react-icons/fa";

import "./Sidebar.css";

function Sidebar() {

    const menu = [
        { name: "Dashboard", path: "/dashboard", icon: <FaTachometerAlt /> },
        { name: "Organizations", path: "/organizations", icon: <FaBuilding /> },
        { name: "Projects", path: "/projects", icon: <FaFolder /> },
        { name: "Queues", path: "/queues", icon: <FaLayerGroup /> },
        { name: "Jobs", path: "/jobs", icon: <FaBriefcase /> },
        { name: "Workers", path: "/workers", icon: <FaServer /> },
        { name: "Execution History", path: "/execution-history", icon: <FaHistory /> },
        { name: "Dead Letter Queue", path: "/dead-letter", icon: <FaExclamationTriangle /> }
    ];

    return (

        <aside className="sidebar">

            <div className="logo">
                Job Scheduler
            </div>

            <nav>

                {menu.map((item) => (

                    <NavLink
                        key={item.path}
                        to={item.path}
                        className={({ isActive }) =>
                            isActive ? "nav-link active" : "nav-link"
                        }
                    >

                        <span>{item.icon}</span>

                        {item.name}

                    </NavLink>

                ))}

            </nav>

        </aside>

    );

}

export default Sidebar;