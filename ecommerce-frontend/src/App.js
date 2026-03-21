import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./Components/Login"; // We will create this next!
import Navbar from "./Components/Navbar"; // This is our navigation bar that shows on every page
import Products from "./Components/Products"; // This is our main page with products
import Cart from "./Components/Cart"; // This is our shopping cart page
function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        {/* The Default Page */}
        <Route path="/" element={<Products/>} />
        {/* The Login Page */}
        <Route path="/login" element={<Login />} />
        {/* The Cart Page */}
        <Route path="/cart" element={<Cart />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
