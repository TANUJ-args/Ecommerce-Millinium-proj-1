import axios from "axios";
import { useEffect } from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

function AdminPanel() {
    const [users, setUsers] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUsers = async () => {
            const token = localStorage.getItem('token');

            if(!token){
                navigate("/login");
                return;
            }
            
            try{
                const response = await axios.get('http://localhost:8080/admin/users',
                // sending body
                {
                    headers: { Authorization: `Bearer ${token}` }
                });
                
                setUsers(response.data);
            }

            catch (error){
                console.log(error);
            }    
        };

        fetchUsers();
    }, []);

    return (

     <div className="container mt-5">
      <h2>Admin Dashboard: User Management</h2>
      
      <table className="table mt-4">
        <thead className="table-dark">
          <tr>
            <th>Email</th>
            <th>Role</th>
          </tr>
        </thead>
        <tbody>
          {/* THE FACTORY LINE STARTS HERE */}
          {users.map((user) => (
            
            <tr key={user.email}>
              <td>{ user.email}</td>
              <td>
                <span className="badge bg-primary">
                  {user.role}
                </span>
              </td>
            </tr>

          ))}
        </tbody>
      </table>
    </div>
  );

}
export default AdminPanel;
