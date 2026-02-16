import { Component } from '@angular/core';
import { ParkingService } from '../../services/parking.service';
import { ParkingSession } from '../../models/parking';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ReceiptDialogComponent } from '../receipt-dialog/receipt-dialog.component';

@Component({
  selector: 'app-vehicle-exit',
  standalone: false,
  templateUrl: './vehicle-exit.component.html',
  styleUrls: ['./vehicle-exit.component.css']
})
export class VehicleExitComponent {

  vNum: string = '';
  // bill: ParkingSession | null = null; // 👈 We don't need this anymore for the HTML
  errorMessage: string = '';

  constructor(
    private service: ParkingService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  onExit() {
    this.errorMessage = '';

    this.service.exitVehicle(this.vNum).subscribe({
      next: (data) => {
        // 1. Show Toast
        this.snackBar.open('Payment Successful', 'Close', {
          duration: 3000,
          panelClass: ['success-snackbar']
        });
        
        // 2. Open the Professional Receipt Dialog
        this.dialog.open(ReceiptDialogComponent, {
          width: '350px', // Proper receipt width
          data: data,
          disableClose: true // User must click close
        });

        // 3. Reset Form
        this.vNum = ''; 
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Vehicle not found or already exited.';
        this.snackBar.open('Exit Failed', 'Retry', {
          duration: 3000,
          panelClass: ['error-snackbar']
        });
      }
    });
  }
}