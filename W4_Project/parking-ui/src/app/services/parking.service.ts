import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ParkingLot, ParkingSession } from '../models/parking';

@Injectable({
  providedIn: 'root'
})
export class ParkingService {

  // Base API URL (Spring Boot backend)
  private apiUrl = 'http://localhost:8081/api';

  // Inject HttpClient for API calls
  constructor(private http: HttpClient) { }

  // Fetch all parking lots
  getParkingLots(): Observable<ParkingLot[]> {
    return this.http.get<ParkingLot[]>(`${this.apiUrl}/parking-lots`);
  }

  // Send vehicle entry request
  enterVehicle(
    lotId: number,
    vehicleNumber: string,
    vehicleType: string
  ): Observable<ParkingSession> {
    return this.http.post<ParkingSession>(`${this.apiUrl}/parking/entry`, {
      parkingLotId: lotId,
      vehicleNumber,
      vehicleType
    });
  }

  // Send vehicle exit request
  exitVehicle(vehicleNumber: string): Observable<ParkingSession> {
    return this.http.post<ParkingSession>(`${this.apiUrl}/parking/exit`, {
      vehicleNumber
    });
  }

  // Fetch active parking sessions
  getActiveSessions(): Observable<ParkingSession[]> {
    return this.http.get<ParkingSession[]>(`${this.apiUrl}/sessions/active`);
  }

  // Fetch complete parking history
  getHistory(): Observable<ParkingSession[]> {
    return this.http.get<ParkingSession[]>(`${this.apiUrl}/sessions/history`);
  }
}
