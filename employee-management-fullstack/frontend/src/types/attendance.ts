import { Student } from './student';

export type AttendanceStatus = 'PRESENT' | 'ABSENT';

export interface Attendance {
  id: string;
  studentId: string;
  studentName: string;
  employeeId: string;
  date: string;
  status: AttendanceStatus;
}

export interface MarkAttendanceRequest {
  employeeId: string;
  status: AttendanceStatus;
  date?: string;
}

export interface AttendanceStats {
  totalDays: number;
  presentDays: number;
  absentDays: number;
  attendancePercentage: number;
}

export interface StudentAttendanceHistory {
  student: Student;
  attendanceRecords: Attendance[];
  statistics: AttendanceStats;
}
