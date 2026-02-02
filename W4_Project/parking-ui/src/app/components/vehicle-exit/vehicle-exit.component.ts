import { Component } from '@angular/core';
import { ParkingService } from '../../services/parking.service';
import { ParkingSession } from '../../models/parking';

@Component({
  selector: 'app-vehicle-exit',
  standalone: false,
  template: `
   <div class="exit-container">
    <div class="form-box">
      <h3>Vehicle Exit</h3>

      <!-- Display error message if exit fails -->
      <div *ngIf="errorMessage" class="error-msg">{{ errorMessage }}</div>

      <form (ngSubmit)="onExit()">
        <div class="form-group">
          <label>Vehicle Number:</label>
          <input
            type="text"
            [(ngModel)]="vNum"
            name="vNum"
            required
            placeholder="Enter Vehicle No"
          >
        </div>
        <button type="submit">Process Exit</button>
      </form>
    </div>

    <!-- Display bill/receipt after successful exit -->
    <div *ngIf="bill" class="receipt-box">
      <div class="receipt-header">PAYMENT RECEIPT</div>

      <p><strong>Vehicle:</strong> {{ bill.vehicleNumber || bill.vehicle?.vehicleNumber }}</p>
      <p><strong>Entry Time:</strong> {{ bill.entryTime | date:'medium' }}</p>
      <p><strong>Exit Time:</strong> {{ bill.exitTime | date:'medium' }}</p>
      <hr style="border-top: 1px dashed #ccc;">

      <div class="receipt-total">
        TOTAL AMOUNT: {{ bill.totalAmount }}
      </div>

      <p><strong>Status:</strong> {{ bill.status }}</p>

      <!-- Billing rules note -->
      <div class="receipt-notes">
        • First 30 minutes are free<br>
        • Minimum billing unit is 1 hour
      </div>
    </div>
  </div>
  `
})
export class VehicleExitComponent {

  // Vehicle number entered by user
  vNum: string = '';

  // Stores bill details after exit
  bill: ParkingSession | null = null;

  // Stores error message from backend
  errorMessage: string = '';

  // Inject ParkingService
  constructor(private service: ParkingService) {}

  // Handle vehicle exit request
  onExit() {
    this.errorMessage = '';
    this.bill = null;

    this.service.exitVehicle(this.vNum).subscribe({
      next: (data) => this.bill = data,
      error: (err) =>
        this.errorMessage = err.error?.message || 'Exit failed. Vehicle not found.'
    });
  }
}
