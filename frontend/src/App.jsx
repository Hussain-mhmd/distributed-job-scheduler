import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Organizations from "./pages/Organizations";
import Projects from "./pages/Projects";
import Queues from "./pages/Queues";
import Jobs from "./pages/Jobs";
import Workers from "./pages/Workers";
import DeadLetter from "./pages/DeadLetter";
import ExecutionHistory from "./pages/ExecutionHistory";

import ProtectedRoute from "./components/ProtectedRoute";
import DashboardLayout from "./layouts/DashboardLayout";

function App() {
  return (
    <BrowserRouter>

      <Routes>

        <Route path="/" element={<Login />} />

        <Route
          element={
            <ProtectedRoute>
              <DashboardLayout />
            </ProtectedRoute>
          }
        >
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/organizations" element={<Organizations />} />
          <Route path="/projects" element={<Projects />} />
          <Route path="/queues" element={<Queues />} />
          <Route path="/jobs" element={<Jobs />} />
          <Route path="/workers" element={<Workers />} />
          <Route path="/dead-letter" element={<DeadLetter />} />
          <Route path="/execution-history" element={<ExecutionHistory />} />
        </Route>

      </Routes>

    </BrowserRouter>
  );
}

export default App;