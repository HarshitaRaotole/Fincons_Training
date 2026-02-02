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
  duration?: string;
  occupancyMultiplier?: number;
  basePrice?: number;
  totalAmount?: number;

  // Current session status
  status: string;
}
