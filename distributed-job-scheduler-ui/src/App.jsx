import Login from "./pages/login";
import Dashboard from "./pages/dashboard";

function App() {

  const token = localStorage.getItem("token");

  return token ? <Dashboard /> : <Login />;

}

export default App;