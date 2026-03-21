import React from "react";
import { Link, useNavigate } from "react-router-dom";

function Navbar() {
  const navigate = useNavigate();

  // Check if the user is logged in by looking for the token
  const token = localStorage.getItem("token");

  // Optional: Grab the user's name if you saved it (based on your screenshot!)
  const userString = localStorage.getItem("user");
  const user = userString ? JSON.parse(userString) : null;

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    navigate("/login"); // Teleport them back to login
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
      <div className="container">
        {/* The Brand Logo/Link */}
        <Link className="navbar-brand fw-bold" to="/">
          Vizag Gear Hub
        </Link>

        <div className="d-flex align-items-center">
          {token ? (
            // WHAT TO SHOW IF LOGGED IN
            <>
              <span className="text-light me-3">
                Hello, {user ? user.name : "Shopper"}!
              </span>
              <Link className="btn btn-outline-light me-2" to="/cart">
                🛒 Cart
              </Link>
              <button className="btn btn-danger" onClick={handleLogout}>
                Logout
              </button>
            </>
          ) : (
            // WHAT TO SHOW IF NOT LOGGED IN
            <Link className="btn btn-primary" to="/login">
              Login
            </Link>
          )}
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
