import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FridgeService } from '../../services/fridge.service';
import { FridgeItemDetailsApi } from '../../models/fridge-item.models';
import { FridgeItemService } from '../../services/fridge-item.service';


@Component({
  selector: 'app-fridge-items-component',
  imports: [CommonModule, FormsModule, DatePipe],
  templateUrl: './fridge-items-component.html',
  styleUrl: './fridge-items-component.css',
})
export class FridgeItemsComponent implements OnInit {
  fridgeId!: number;
  items: FridgeItemDetailsApi[] = [];

  page = 0;
  size = 10;
  totalPages = 0;

  nameFilter: string = '';
  categoryFilter: string = '';
  storedOnFrom: string = '';
  storedOnTo: string = '';
  expiresOnFrom: string = '';
  expiresOnTo: string = '';
  onlyExpired: boolean | null = null;

  sortField: string = 'name';
  sortDirection: 'asc' | 'desc' = 'asc';

  deleteErrorMessages: string[] = [];
  deleteSuccess: boolean | null = null;

newItem = {
  name: '',
  quantity: null as number | null,
  measurementUnit: '',
  category: '',
  storedOn: '',
  expiresOn: '',
  note: ''
};

categories = [
  'DAIRY', 'MEAT', 'FISH', 'VEGETABLES', 'FRUITS',
  'BEVERAGES', 'BAKERY', 'FROZEN', 'CONDIMENTS', 'SNACKS',
  'HERBS_SPICES', 'READY_MEALS', 'OTHER'
]

addSuccess: boolean | null = null;
addErrorMessages: string[] = [];

takeAmount: { [itemId: number]: number | null } = {};

takeSuccess: boolean | null = null;
takeErrorMessages: string[] = [];

  constructor(
    private route: ActivatedRoute,
    private fridgeItemService: FridgeItemService
  ) {}

  ngOnInit() {
    this.fridgeId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadItems();
  }

  loadItems() {
    const filters = {
      name: this.nameFilter,
      category: this.categoryFilter,
      storedOnFrom: this.storedOnFrom,
      storedOnTo: this.storedOnTo,
      expiresOnFrom: this.expiresOnFrom,
      expiresOnTo: this.expiresOnTo,
      onlyExpired: this.onlyExpired
    };

    const sortParam = [`${this.sortField}-${this.sortDirection}`];

    this.fridgeItemService
    .getFridgeItems(this.fridgeId, this.page, this.size, filters, sortParam)
    .subscribe(response => {
      this.items = response.content;
      this.totalPages = response.totalPages;
  });
  }

  applyFilters() {
    this.page = 0;
    this.loadItems();
  }

  changeSort(field: string) {
    if (this.sortField === field) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = field;
      this.sortDirection = 'asc';
    }
    this.loadItems();
  }

  nextPage() {
    if (this.page + 1 < this.totalPages) {
      this.page++;
      this.loadItems();
    }
  }

  previousPage() {
    if (this.page > 0) {
      this.page--;
      this.loadItems();
    }
  }

  deleteItem(itemId: number, name: string) {
    this.deleteErrorMessages = [];
    this.deleteSuccess = null;
  
    const confirmed = confirm(`Are you sure you want to delete item "${name}"?`);
    if (!confirmed) {
      return;
    }
  
    this.fridgeItemService.deleteFridgeItem(itemId).subscribe({
      next: (resp) => {
        this.deleteSuccess = resp.success;
    
        if (resp.success) {
          this.loadItems();
        }
      },
      error: (err) => {
        if (err.error?.messages) {
          this.deleteErrorMessages = err.error.messages.map((m: any) => m.label);
        } else {
          this.deleteErrorMessages = ["An unexpected error occurred while deleting the item."];
        }
      }
    });
    
  }

  addItem() {
    this.addSuccess = null;
    this.addErrorMessages = [];
  
    // basic client-side validation
    if (!this.newItem.name || !this.newItem.quantity || !this.newItem.measurementUnit ||
        !this.newItem.category || !this.newItem.storedOn || !this.newItem.expiresOn) {
  
      this.addErrorMessages = ["All required fields must be filled."];
      return;
    }
  
    const body = {
      name: this.newItem.name,
      quantity: this.newItem.quantity,
      measurementUnit: this.newItem.measurementUnit,
      category: this.newItem.category,
      storedOn: new Date(this.newItem.storedOn).toISOString(),
      expiresOn: this.newItem.expiresOn,
      note: this.newItem.note
    };
  
    this.fridgeItemService.addFridgeItem(this.fridgeId, body)
      .subscribe({
        next: (resp) => {
          this.addSuccess = resp.success;
  
          if (resp.success) {
            this.newItem = {
              name: '',
              quantity: null,
              measurementUnit: '',
              category: '',
              storedOn: '',
              expiresOn: '',
              note: ''
            };
  
            this.loadItems(); 
          }
        },
        error: (err) => {
          if (err.error?.messages) {
            this.addErrorMessages = err.error.messages.map((m: any) => m.label);
          } else {
            this.addErrorMessages = ["Unexpected error occurred while adding item."];
          }
        }
      });
  }

  takeItem(itemId: number, name: string) {
    this.takeSuccess = null;
    this.takeErrorMessages = [];
  
    const qty = this.takeAmount[itemId];
  
    if (qty === null || qty === undefined || qty <= 0) {
      this.takeErrorMessages = ["Quantity must be greater than 0."];
      return;
    }
  
    this.fridgeItemService.takeFridgeItem(itemId, qty).subscribe({
      next: resp => {
        this.takeSuccess = resp.success;
  
        if (resp.success) {
          this.takeAmount[itemId] = null; // reset input
          this.loadItems(); // refresh list
        }
      },
      error: err => {
        if (err.error?.messages) {
          this.takeErrorMessages = err.error.messages.map((m: any) => m.label);
        } else {
          this.takeErrorMessages = ["Unexpected error occurred while taking the item."];
        }
      }
    });
  }
  
  
}
