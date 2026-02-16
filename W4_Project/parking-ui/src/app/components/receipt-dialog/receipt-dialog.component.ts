import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ParkingSession } from '../../models/parking';

@Component({
  selector: 'app-receipt-dialog',
  standalone: false,
  template: `
    <div class="receipt-paper">
      
      <!-- Header -->
      <div class="receipt-header">
        <h3>PARKING RECEIPT</h3>
        <p>THANK YOU FOR VISITING</p>
      </div>
      
      <div class="dashed-line"></div>

      <!-- Details Body -->
      <div class="receipt-body">
        <div class="receipt-row">
          <span>Vehicle:</span>
          <strong>{{ data.vehicleNumber || data.vehicle?.vehicleNumber }}</strong>
        </div>
        <div class="receipt-row">
          <span>Entry:</span>
          <span>{{ data.entryTime | date:'medium' }}</span>
        </div>
        <div class="receipt-row">
          <span>Exit:</span>
          <span>{{ data.exitTime | date:'medium' }}</span>
        </div>
      </div>

      <div class="dashed-line"></div>

      <!-- Total Amount -->
      <div class="receipt-total">
        <span>TOTAL PAID</span>
        <h1 class="amount">{{ data.totalAmount | currency:'INR' }}</h1>
      </div>

      <!-- Footer Button -->
      <div class="receipt-footer">
        <button mat-flat-button color="primary" (click)="close()">Close Receipt</button>
      </div>

    </div>
  `,
  styles: [`
    /* Container representing the paper */
    .receipt-paper {
      background: #fff;
      padding: 20px;
      text-align: center;
      border-top: 5px solid #333; /* Dark top strip */
      position: relative;
      /* We don't need the zig-zag bottom CSS here because Dialogs 
         cut off overflow, but the border-top makes it look pro */
    }

    /* Header */
    .receipt-header h3 { margin: 0; letter-spacing: 2px; color: #333; font-weight: 700; }
    .receipt-header p { margin: 5px 0 0; font-size: 11px; color: #777; text-transform: uppercase; }

    /* Separator */
    .dashed-line { border-bottom: 2px dashed #ddd; margin: 15px 0; }

    /* Rows */
    .receipt-row {
      display: flex;
      justify-content: space-between;
      margin-bottom: 8px;
      font-size: 14px;
      color: #444;
      text-align: left;
    }
    .receipt-row span:last-child { text-align: right; }

    /* Total Box */
    .receipt-total { background: #f9f9f9; padding: 15px; border-radius: 4px; }
    .receipt-total span { font-size: 11px; font-weight: bold; color: #777; letter-spacing: 1px; display: block; }
    .amount { margin: 5px 0 0; color: #2e7d32; font-weight: 700; font-size: 28px; }

    /* Button */
    .receipt-footer { margin-top: 20px; }
    button { width: 100%; }
  `]
})
export class ReceiptDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<ReceiptDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ParkingSession
  ) {}

  close() {
    this.dialogRef.close();
  }
}