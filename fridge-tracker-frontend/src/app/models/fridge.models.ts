export interface FridgeDetailsApi {
    id: number;
    name: string;
    createdOn: string; 
  }
  
export interface FridgePage {
    content: FridgeDetailsApi[];
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
    hasNext: boolean;
    hasPrevious: boolean;
}
  