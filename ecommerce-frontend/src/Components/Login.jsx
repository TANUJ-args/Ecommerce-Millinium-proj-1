import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate(); // The React Teleporter

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/auth/login", {
        email: email,
        password: password,
      });

      // Save the VIP Pass
      localStorage.setItem("token", response.data.accessToken);

      // Teleport the user back to the Dashboard!
      navigate("/");
    } catch (err) {
      setError("Invalid email or password");
    }
  };

  return (
    <div className="container mt-5" style={{ maxWidth: "400px" }}>
      <div className="card shadow-sm">
        <div className="card-body">
          <h3 className="card-title text-center mb-4">Login</h3>

          {error && <div className="alert alert-danger">{error}</div>}

          <form onSubmit={handleLogin}>
            <div className="mb-3">
              <label className="form-label">Email</label>
              <input
                type="text"
                className="form-control"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
            <div className="mb-4">
              <label className="form-label">Password</label>
              <input
                type="password"
                className="form-control"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            <button type="submit" className="btn btn-primary w-100 fw-bold">
              Sign In
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Login;
