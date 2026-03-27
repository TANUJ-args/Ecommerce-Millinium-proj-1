import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import api from "../axiosConfig"; // <-- Using your new Interceptor!

function Products() {
  const [products, setProducts] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      // Clean GET request! The base URL is already handled by the interceptor.
      // Note: Adjust the endpoint if your Java controller uses just '/api/products'
      const response = await api.get("/api/products/all");
      setProducts(response.data);
    } catch (error) {
      console.error("Error fetching products:", error);
    }
  };

  const handleAddToCart = async (productId) => {
    // We still check if they are logged in so we can bounce them to the login page if not
    const token = localStorage.getItem("token");
    if (!token) {
      navigate("/login");
      return;
    }

    try {
      // Clean POST request! The interceptor securely attaches the JWT token automatically.
      const response = await api.post(
        `/api/cart/add?productId=${productId}&quantity=1`,
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
      <h2 className="mb-4 fw-bold">Vizag Gear Hub - Products</h2>

      <div className="d-flex flex-wrap gap-4">
        {products.map((product) => (
          <div
            className="card shadow-sm"
            style={{ width: "18rem" }}
            key={product.id}
          >
            {/* Placeholder Image */}
            <img
              src={`https://via.placeholder.com/300x200?text=${encodeURIComponent(product.name)}`}
              className="card-img-top"
              alt={product.name}
            />

            <div className="card-body d-flex flex-column">
              <h5 className="card-title fw-bold">{product.name}</h5>
              <p className="card-text text-truncate text-muted">
                {product.description}
              </p>

              {/* mt-auto pushes the price and button to the bottom so all cards are equal height */}
              <div className="d-flex justify-content-between align-items-center mb-3 mt-auto">
                <span className="fs-5 fw-bold text-success">
                  ₹{product.price}
                </span>
                <span className="badge bg-secondary">
                  {/* Fallback to 0 if stock is undefined */}
                  Stock: {product.stockQuantity || product.stock || 0}
                </span>
              </div>

              <button
                className="btn btn-primary w-100 fw-bold shadow-sm"
                onClick={() => handleAddToCart(product.id)}
              >
                Add to Cart
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Products;
