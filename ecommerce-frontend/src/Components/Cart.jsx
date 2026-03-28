import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import api from "../axiosConfig"; 
function Cart() {
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  const getItemPrice = (item) => {
    const candidates = [
      item?.price,
      item?.product?.price,
      item?.productPrice,
      item?.unitPrice,
      item?.unit_price,
      item?.amount,
      item?.totalPrice,
      item?.total,
    ];
    const found = candidates.find(
      (value) => typeof value === "number" && !Number.isNaN(value),
    );
    if (
      found !== undefined &&
      (item?.quantity || item?.qty) &&
      (found === item?.totalPrice || found === item?.total)
    ) {
      const qty = item?.quantity || item?.qty || 1;
      return qty > 0 ? found / qty : 0;
    }
    return found ?? 0;
  };

  const getItemQuantity = (item) => item?.quantity ?? item?.qty ?? 1;

  const getItemName = (item) =>
    item?.name || item?.product?.name || item?.productName || "Unknown Product";

  const getItemTotal = (item) => {
    const explicitTotal = item?.totalPrice ?? item?.total;
    if (typeof explicitTotal === "number" && !Number.isNaN(explicitTotal)) {
      return explicitTotal;
    }
    return getItemPrice(item) * getItemQuantity(item);
  };

  useEffect(() => {
    fetchCart();
  }, []);

  const fetchCart = async () => {
   

    try {
      // THE NEW WAY (DRY Code)
      const response = await api.get('/api/cart');

      console.log("Raw Cart Data from Java:", response.data);

      if (Array.isArray(response.data)) {
        setCartItems(response.data);
      } else if (response.data && response.data.cartItems) {
        setCartItems(response.data.cartItems);
      } else if (response.data && response.data.items) {
        setCartItems(response.data.items);
      } else {
        setCartItems([]);
      }

      setLoading(false);
    } catch (error) {
      console.error("Error fetching cart", error);
      setLoading(false);
    }
  };

  // --- THE BULLETPROOF MATH ---
  const calculateTotal = () => {
    return cartItems.reduce((total, item) => total + getItemTotal(item), 0);
  };

  if (loading) return <div className="container mt-5">Loading cart...</div>;

  return (
    <div className="container mt-5">
      <h2>Your Shopping Cart</h2>
      {cartItems.length === 0 ? (
        <p>Your cart is empty.</p>
      ) : (
        <>
          <table className="table mt-4">
            <thead>
              <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Total</th>
              </tr>
            </thead>
            <tbody>
              {cartItems.map((item, index) => (
                <tr key={item?.id || index}>
                  <td>{getItemName(item)}</td>
                  <td>₹{getItemPrice(item)}</td>
                  <td>{getItemQuantity(item)}</td>
                  <td>₹{getItemTotal(item)}</td>
                </tr>
              ))}
            </tbody>
          </table>
          <div className="d-flex justify-content-end align-items-center mt-4">
            <h4 className="me-4">
              Grand Total:{" "}
              <span className="text-success">₹{calculateTotal()}</span>
            </h4>
            <button className="btn btn-success btn-lg">
              Proceed to Checkout
            </button>
          </div>
        </>
      )}
    </div>
  );
}

export default Cart;
