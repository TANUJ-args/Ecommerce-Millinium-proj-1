import axios from "axios";

// 1. Create a custom version of Axios pointing to your Java server
const api = axios.create({
  baseURL: "http://localhost:8080", // Now you never have to type this URL again!
});

// 2. THE TOLL BOOTH (The Interceptor)
// This code runs automatically BEFORE any request leaves your React app
api.interceptors.request.use(
  (config) => {
    // Check local storage for the VIP token
    const token = localStorage.getItem("token");

    // If the token exists, attach it to the Authorization header
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    // Let the request continue to Java
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

// Export our custom 'api' so the rest of the app can use it
export default api;
