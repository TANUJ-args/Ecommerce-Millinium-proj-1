import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function Register() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    setError("");
    try {
      await axios.post("http://localhost:8080/auth/register", {
        email: email,
        password: password,
        role: role,
      });

      navigate("/login");
    } catch (err) {
      const responseBody = err?.response?.data;
      const serverMessage =
        (typeof responseBody === "string" && responseBody) ||
        responseBody?.message ||
        responseBody?.error ||
        err?.message;

      setError(serverMessage || "Server error");
    }
  };

  return (
    <div className="container mt-5" style={{ maxWidth: "400px" }}>
      <div className="card shadow-sm">
        <div className="card-body">
          <h3 className="card-title text-center mb-4">Register</h3>

          <form onSubmit={handleRegister}>
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
            <div className="mb-4">
              <label className="form-label">Role</label>
              <input
                type="text"
                className="form-control"
                value={role}
                onChange={(e) => setRole(e.target.value)}
                required
              />
            </div>
            <button type="submit" className="btn btn-primary w-100 fw-bold">
              Register
            </button>
            <a className="btn btn-link w-100" href="/login">
              Now login with those credentials
            </a>
            
            {error && <div className="alert alert-danger">{error}</div>}

          </form>
        </div>
      </div>
    </div>
  );
}

export default Register;
