import { Component, OnInit } from '@angular/core';
import { ParkingService } from '../../services/parking.service';
import { ParkingSession } from '../../models/parking';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-parking-history',
  standalone: false,
  templateUrl: './parking-history.component.html',
  styleUrls: ['./parking-history.component.css']
})
export class ParkingHistoryComponent implements OnInit {

  //  Columns for Material Table
  displayedColumns: string[] = ['id', 'vehicleNumber', 'slotNumber', 'entryTime', 'exitTime', 'amount', 'status'];

  sessions: ParkingSession[] = [];

  // Pagination State
  totalElements: number = 0;
  currentPage: number = 0;
  pageSize: number = 10;
  keyword: string = '';

  constructor(private service: ParkingService) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.service.getHistory(this.currentPage, this.pageSize, this.keyword)
      .subscribe(response => {
        this.sessions = response.content;
        this.totalElements = response.totalElements; // Required for Paginator
      });
  }

  onSearch() {
    this.currentPage = 0;
    this.loadData();
  }

  onReset() {
    this.keyword = '';
    this.onSearch();
  }

  // Handle Page Changes
  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadData();
  }
}