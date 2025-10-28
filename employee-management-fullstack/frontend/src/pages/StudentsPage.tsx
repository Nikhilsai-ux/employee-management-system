import { useEffect, useState } from 'react';
import { useStudentStore } from '../store/useStudentStore';
import { studentsApi } from '../api';
import type { CreateStudentRequest } from '../types';

export function StudentsPage() {
  const { students, loading, error, fetchStudents, refreshStudents } = useStudentStore();
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState<CreateStudentRequest>({
    name: '',
    employeeId: '',
    email: '',
    phone: '',
    department: '',
    year: 1,
  });
  const [message, setMessage] = useState<{ type: 'success' | 'error'; text: string } | null>(null);

  useEffect(() => {
    fetchStudents();
  }, [fetchStudents]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const response = await studentsApi.create(formData);
    
    if (response.success) {
      setMessage({ type: 'success', text: 'Employee added successfully!' });
      setFormData({ name: '', employeeId: '', email: '', phone: '', department: '', year: 1 });
      setShowForm(false);
      refreshStudents();
    } else {
      setMessage({ type: 'error', text: response.error || 'Failed to add employee' });
    }
  };

  if (loading) return <div className="loading">Loading...</div>;
  if (error) return <div className="error">Error: {error}</div>;

  return (
    <div className="page">
      <h1>Employees</h1>
      
      {message && (
        <div className={`message ${message.type}`}>
          {message.text}
        </div>
      )}

      <button onClick={() => setShowForm(!showForm)} className="btn-primary">
        {showForm ? 'Cancel' : 'Add New Employee'}
      </button>

      {showForm && (
        <form onSubmit={handleSubmit} className="form">
          <div className="form-grid">
            <input
              type="text"
              placeholder="Name"
              value={formData.name}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              required
            />
            <input
              type="text"
              placeholder="Employee ID"
              value={formData.employeeId}
              onChange={(e) => setFormData({ ...formData, employeeId: e.target.value.toUpperCase() })}
              required
            />
            <input
              type="email"
              placeholder="Email"
              value={formData.email}
              onChange={(e) => setFormData({ ...formData, email: e.target.value })}
            />
            <input
              type="tel"
              placeholder="Phone"
              value={formData.phone}
              onChange={(e) => setFormData({ ...formData, phone: e.target.value })}
            />
            <input
              type="text"
              placeholder="Department"
              value={formData.department}
              onChange={(e) => setFormData({ ...formData, department: e.target.value })}
            />
            <select
              value={formData.year}
              onChange={(e) => setFormData({ ...formData, year: parseInt(e.target.value) })}
            >
              <option value={1}>Level 1</option>
              <option value={2}>Level 2</option>
              <option value={3}>Level 3</option>
              <option value={4}>Level 4</option>
            </select>
          </div>
          <button type="submit" className="btn-success">Add Employee</button>
        </form>
      )}

      <div className="students-list">
        <h2>All Employees ({students.length})</h2>
        {students.length === 0 ? (
          <p>No employees found. Add your first employee!</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Employee ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Department</th>
                <th>Level</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {students.map((student) => (
                <tr key={student.id}>
                  <td><strong>{student.employeeId}</strong></td>
                  <td>{student.name}</td>
                  <td>{student.email || '—'}</td>
                  <td>{student.phone || '—'}</td>
                  <td>{student.department || '—'}</td>
                  <td>{student.year ? `Level ${student.year}` : '—'}</td>
                  <td>{student.active ? '✅ Active' : '❌ Inactive'}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}
