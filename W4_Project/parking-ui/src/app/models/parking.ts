export interface Page<T> {
  content: T[];          // The actual list of data
  totalPages: number;    // Total number of pages
  totalElements: number; // Total rows in DB
  number: number;        // Current page number (0-based)
  size: number;          // Page size
  first: boolean;
  last: boolean;
}

export interface ParkingLot {
  // Core parking lot fields
  id: number;
  name: string;
  location: string;
  totalSlots: number;
  basePricePerHour: number;

  // Optional fields to handle different backend response formats
  parkingLotName?: string;
  total_slots?: number;
  basePrice?: number;
}

export interface ParkingSession {
  // Session identifier
  id: number;

  // Flat fields (used in some API responses)
  vehicleNumber?: string;
  vehicleType?: string;
  slotNumber?: string;

  // Nested vehicle details
  vehicle?: {
    vehicleNumber: string;
    vehicleType: string;
  };

  // Nested slot and parking lot details
  parkingSlot?: {
    slotNumber: number;
    parkingLot?: {
      id: number;
      name: string;
      location: string;
    };
  };

  // Session timing and billing details
  entryTime: string;
  exitTime?: string;

  totalAmount?: number;

  // Current session status
  status: string;
}
