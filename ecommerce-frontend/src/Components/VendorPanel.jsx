import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import api from '../axiosConfig';
function AddProduct() {
  // 1. THE MEMORY: Buckets for what the user types
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [price, setPrice] = useState("");
  const [stock, setStock] = useState("");

  const navigate = useNavigate();

  // 3. THE INTERCEPTOR: What happens when they click "Save"
  const handleSubmit = async (e) => {
    // CRITICAL: This stops the browser from refreshing the page!
    e.preventDefault();

    // Pack the JSON box exactly how Java expects it
    const newProduct = {
      name: name,
      description: description,
      price: parseFloat(price), // Convert the text input to a decimal number
      stockQuantity: parseInt(stock), // Convert text to a whole number
    };
 
    try {
      // Send the POST request. with newproducts 
      const response = await api.post("/api/vendor/addproduct", newProduct);
      console.log("Saved Product:", response.data);
      
      alert("Product added successfully!");

      // Teleport back to the dashboard so you can see your new item
      navigate("/");
    } catch (error) {
      console.error("Error saving product:", error);
      alert("Failed to save product. Check the console.");
    }
  };

  return (
    <div className="container mt-5" style={{ maxWidth: "600px" }}>
      <h2 className="mb-4">Add New Gear</h2>

      <div className="card shadow-sm">
        <div className="card-body">
          {/* We attach our Interceptor function to the form's onSubmit */}
          <form onSubmit={handleSubmit}>
            {/* 2. THE BINDING: Notice value={} and onChange={} */}
            <div className="mb-3">
              <label className="form-label fw-bold">Product Name</label>
              <input
                type="text"
                className="form-control"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </div>

            <div className="mb-3">
              <label className="form-label fw-bold">Description</label>
              <textarea
                className="form-control"
                rows="3"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                required
              />
            </div>

            <div className="row">
              <div className="col-md-6 mb-3">
                <label className="form-label fw-bold">Price (₹)</label>
                <input
                  type="number"
                  className="form-control"
                  value={price}
                  onChange={(e) => setPrice(e.target.value)}
                  required
                />
              </div>
              <div className="col-md-6 mb-3">
                <label className="form-label fw-bold">Stock Quantity</label>
                <input
                  type="number"
                  className="form-control"
                  value={stock}
                  onChange={(e) => setStock(e.target.value)}
                  required
                />
              </div>
            </div>

            <button
              type="submit"
              className="btn btn-success w-100 mt-3 fw-bold"
            >
              Save Product
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default AddProduct;
