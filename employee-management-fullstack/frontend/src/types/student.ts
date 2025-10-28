export interface Student {
  id: string;
  name: string;
  employeeId: string;
  email?: string;
  phone?: string;
  department?: string;
  year?: number;
  active: boolean;
}

export interface CreateStudentRequest {
  name: string;
  employeeId: string;
  email?: string;
  phone?: string;
  department?: string;
  year?: number;
}

export interface UpdateStudentRequest {
  name?: string;
  email?: string;
  phone?: string;
  department?: string;
  year?: number;
  active?: boolean;
}
