export interface FridgeItemDetailsApi {
    id: number;
    name: string;
    quantity: number;
    measurementUnit: string;
    category: string;
    storedOn: string;
    expiresOn: string;
    note: string;
}
  
export interface FridgeItemPage {
    content: FridgeItemDetailsApi[];
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
    hasNext: boolean;
    hasPrevious: boolean;
}
  