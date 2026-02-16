import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page, ParkingLot, ParkingSession } from '../models/parking';

@Injectable({
  providedIn: 'root'
})
export class ParkingService {

  // Base API URL (Spring Boot backend)
  private apiUrl = 'http://localhost:8081/api';

  // Inject HttpClient for API calls
  constructor(private http: HttpClient) { }

  // Fetch all parking lots
  getParkingLots(page: number, size: number, keyword: string): Observable<Page<ParkingLot>> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size);
    
    if (keyword) params = params.set('keyword', keyword);

    return this.http.get<Page<ParkingLot>>(`${this.apiUrl}/parking-lots`, { params });
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
  getActiveSessions(page: number, size: number, keyword: string): Observable<Page<ParkingSession>> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size);

    if (keyword) params = params.set('keyword', keyword);

    return this.http.get<Page<ParkingSession>>(`${this.apiUrl}/sessions/active`, { params });
  }

  // Fetch complete parking history
  getHistory(page: number, size: number, keyword: string): Observable<Page<ParkingSession>> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size);

    if (keyword) params = params.set('keyword', keyword);

    return this.http.get<Page<ParkingSession>>(`${this.apiUrl}/sessions/history`, { params });
  }
}
