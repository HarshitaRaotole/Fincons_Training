import { Component, OnInit } from '@angular/core';
import { ParkingService } from '../../services/parking.service';
import { ParkingLot } from '../../models/parking';

@Component({
  selector: 'app-parking-lots',
  standalone: false,
  template: `
    <!-- Displays list of available parking lots -->
    <h3>Available Parking Lots</h3>

    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Location</th>
          <th>Total Slots</th>
          <th>Base Price/Hr</th>
        </tr>
      </thead>

      <tbody>
        <!-- Loop through parking lots -->
        <tr *ngFor="let lot of lots">
          <td>{{ lot.id }}</td>

          <!-- Handle different name field mappings -->
          <td>{{ lot.name || lot.parkingLotName }}</td>

          <td>{{ lot.location }}</td>

          <!-- Handle different totalSlots naming -->
          <td>{{ lot.totalSlots || lot.total_slots }}</td>

          <!-- Handle different base price naming -->
          <td>{{ lot.basePricePerHour || lot.basePrice }}</td>
        </tr>
      </tbody>
    </table>
  `
})
export class ParkingLotsComponent implements OnInit {

  // Stores list of parking lots
  lots: ParkingLot[] = [];

  // Inject ParkingService to fetch parking lot data
  constructor(private service: ParkingService) {}

  // Load parking lots on component initialization
  ngOnInit() {
    this.service.getParkingLots().subscribe(data => this.lots = data);
  }
}
