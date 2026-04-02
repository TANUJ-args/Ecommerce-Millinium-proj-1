import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Register from "./Components/Register";
import Login from "./Components/Login"; // We will create this next!
import Navbar from "./Components/Navbar"; // This is our navigation bar that shows on every page
import Products from "./Components/Products"; // This is our main page with products
import Cart from "./Components/Cart"; // This is our shopping cart page
import AdminPanel from "./Components/AdminPanel"; // This is our admin page to read users
import AddProducts from "./Components/VendorPanel"; // This is our vendor page to add products
import ProtectedRoute from "./Components/ProtectedRoute"; //protected route for all pages for more security
function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        {/* The Default Page */}
        <Route
          path="/"
          element={
            <ProtectedRoute>
              <Products />
            </ProtectedRoute>
          }
        />
        {/* The Register Page */}
        <Route path="/register" element={<Register />} />
        {/* The Login Page */}
        <Route path="/login" element={<Login />} />
        {/* The Cart Page */}
        <Route path="/cart" element={<Cart />} />
        {/* The Admin Page */}
        <Route
          path="/admin"
          element={
            <ProtectedRoute>
              <AdminPanel />
            </ProtectedRoute>
          }
        />
        {/* The Vendor Page */}
        <Route
          path="/vendor"
          element={
            <ProtectedRoute>
              <AddProducts />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
