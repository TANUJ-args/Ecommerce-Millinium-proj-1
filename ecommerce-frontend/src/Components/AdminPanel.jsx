import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../axiosConfig";

function AdminPanel() {
  const [users, setUsers] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        // Look how clean this is! The interceptor handles the localhost URL and the token.
        const response = await api.get("/admin/users");
        setUsers(response.data);
      } catch (error) {
        console.error("Failed to fetch users:", error);

        // If Java blocks them (403 Forbidden), they aren't a real admin. Kick them out!
        if (error.response && error.response.status === 403) {
          alert("Access Denied: You do not have Admin privileges.");
          navigate("/");
        }
      }
    };

    fetchUsers();
  }, [navigate]);

  return (
    <div className="container mt-5">
      <h2 className="fw-bold mb-4">Admin Dashboard: User Management</h2>

      <div className="card shadow-sm">
        <div className="card-body p-0">
          <table className="table table-hover mb-0">
            <thead className="table-dark">
              <tr>
                <th>Email</th>
                <th>Role</th>
              </tr>
            </thead>
            <tbody>
              {/* THE FACTORY LINE */}
              {users.length > 0 ? (
                users.map((user) => (
                  <tr key={user.email}>
                    <td className="align-middle">{user.email}</td>
                    <td>
                      {/* Dynamic styling: Admins get a red badge, Users get blue */}
                      <span
                        className={`badge ${user.role === "ADMIN" ? "bg-danger" : "bg-primary"}`}
                      >
                        {user.role}
                      </span>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="2" className="text-center py-4 text-muted">
                    Loading users...
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default AdminPanel;
