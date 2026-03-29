import React from "react";
import { Navigate } from "react-router-dom";

function ProtectedRoute({ children }) {
  // 1. The Bouncer checks for the VIP badge
  const token = localStorage.getItem("token");

  // 2. If there is no token, kick them to the login page
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  // 3. If they have the token, let them into the club (render the page)
  return children;
}

export default ProtectedRoute;
