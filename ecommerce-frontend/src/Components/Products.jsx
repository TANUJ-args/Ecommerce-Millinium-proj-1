import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function Products() {
  const [products, setProducts] = useState([]);
  const navigate = useNavigate();
  useEffect(() => {
    fetch("http://localhost:8080/api/products/all")
      .then((response) => response.json())
      .then((data) => setProducts(data))
      .catch((error) => console.error("Error fetching data:", error));
  }, []);

  const handleAddToCart = async (productId) => {
    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/login");
      return;
    }

    try {
      // THE MISSING ENGINE: Actually sending the data to Java!
      const response = await axios.post(
        `http://localhost:8080/api/cart/add?productId=${productId}&quantity=1`,
        {}, // Empty body because we are using Query Parameters
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );

      console.log("Server response:", response.data);
      alert("Added to Cart successfully!");
    } catch (error) {
      console.error("Cart Error:", error);
      if (error.response && error.response.status === 403) {
        localStorage.removeItem("token");
        navigate("/login");
      } else {
        alert("Failed to add to cart.");
      }
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4">Our Products</h2>

      {/* d-flex and flex-wrap puts the cards side-by-side instead of in one giant vertical line */}
      <div className="d-flex flex-wrap gap-4">
        {products.map((product) => (
          /* --- YOUR BOOTSTRAP CARD STARTS HERE --- */
          <div className="card" style={{ width: "18rem" }} key={product.id}>
            {/* Placeholder image generator that writes the product name on the image */}
            <img
              src={`https://via.placeholder.com/300x200?text=${encodeURIComponent(product.name)}`}
              className="card-img-top"
              alt={product.name}
            />

            <div className="card-body">
              {/* Product Name */}
              <h5 className="card-title">{product.name}</h5>

              {/* Product Description */}
              <p className="card-text text-truncate">{product.description}</p>

              {/* Price and Stock */}
              <div className="d-flex justify-content-between align-items-center mb-3">
                <span className="fs-5 fw-bold text-success">
                  ₹{product.price}
                </span>
                <span className="badge bg-secondary">
                  Stock: {product.stockQuantity}
                </span>
              </div>

              <button
                className="btn btn-primary w-100"
                onClick={() => handleAddToCart(product.id)}
              >
                Add to Cart
              </button>
            </div>
          </div>
          /* --- YOUR BOOTSTRAP CARD ENDS HERE --- */
        ))}
      </div>
    </div>
  );
}

export default Products;
