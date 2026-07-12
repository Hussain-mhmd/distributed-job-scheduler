import "./Navbar.css";

function Navbar() {

    const logout = () => {

        localStorage.removeItem("token");

        window.location.href = "/";

    };

    return (

        <header className="navbar">

            <div>

                <h2>Distributed Job Scheduler</h2>

                <p>
                    Monitor queues, workers and jobs
                </p>

            </div>

            <button
                className="logout-btn"
                onClick={logout}
            >
                Logout
            </button>

        </header>

    );

}

export default Navbar;