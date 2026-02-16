import { Component, OnInit, ViewChild } from '@angular/core';
import { ParkingService } from '../../services/parking.service';
import { ParkingLot } from '../../models/parking';
// ⭐ NEW: Imports for Material Pagination
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-parking-lots',
  standalone: false,
  templateUrl: './parking-lots.component.html', //  CHANGE: Using external file for cleanliness
  styleUrls: ['./parking-lots.component.css']
})
export class ParkingLotsComponent implements OnInit {

  // Define columns for MatTable 
  displayedColumns: string[] = ['id', 'name', 'location', 'totalSlots', 'basePrice'];

  lots: ParkingLot[] = [];

  // Pagination State
  totalElements: number = 0; // Essential for Server-Side Paginator
  currentPage: number = 0;
  pageSize: number = 5;
  keyword: string = '';

  constructor(private service: ParkingService) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.service.getParkingLots(this.currentPage, this.pageSize, this.keyword)
      .subscribe(response => {
        this.lots = response.content;
        
        // We need totalElements for MatPaginator to calculate pages automatically
        this.totalElements = response.totalElements; 
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

  //  Handles Material Paginator Event (Next/Prev/Page Size change)
  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex; 
    this.pageSize = event.pageSize;     // Capture if user changed size from 5 to 10
    this.loadData();
  }
}