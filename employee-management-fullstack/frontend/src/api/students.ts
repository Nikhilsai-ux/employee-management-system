import { apiClient } from './client';
import type {
  Student,
  CreateStudentRequest,
  UpdateStudentRequest,
  ApiResponse,
} from '../types';

export const studentsApi = {
  getAll: async (activeOnly?: boolean): Promise<ApiResponse<Student[]>> => {
    const query = activeOnly ? '?activeOnly=true' : '';
    return apiClient.get<Student[]>(`/employees${query}`);
  },
  
  getById: async (id: string): Promise<ApiResponse<Student>> => {
    return apiClient.get<Student>(`/employees/${id}`);
  },
  
  getByEmployeeId: async (employeeId: string): Promise<ApiResponse<Student>> => {
    return apiClient.get<Student>(`/employees/id/${employeeId}`);
  },
  
  search: async (name: string): Promise<ApiResponse<Student[]>> => {
    return apiClient.get<Student[]>(`/employees/search?name=${encodeURIComponent(name)}`);
  },
  
  getByDepartment: async (department: string): Promise<ApiResponse<Student[]>> => {
    return apiClient.get<Student[]>(`/employees/department/${department}`);
  },
  
  create: async (data: CreateStudentRequest): Promise<ApiResponse<Student>> => {
    return apiClient.post<Student>('/employees', data);
  },
  
  update: async (id: string, data: UpdateStudentRequest): Promise<ApiResponse<Student>> => {
    return apiClient.put<Student>(`/employees/${id}`, data);
  },
  
  delete: async (id: string): Promise<ApiResponse<void>> => {
    return apiClient.delete<void>(`/employees/${id}`);
  },
  
  deactivate: async (id: string): Promise<ApiResponse<void>> => {
    return apiClient.patch<void>(`/employees/${id}/deactivate`);
  },
  
  getActiveCount: async (): Promise<ApiResponse<number>> => {
    return apiClient.get<number>('/employees/stats/count');
  },
};
