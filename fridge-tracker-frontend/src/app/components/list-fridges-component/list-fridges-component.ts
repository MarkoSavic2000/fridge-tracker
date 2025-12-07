import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { FridgeService } from '../../services/fridge.service';
import { FridgeDetailsApi } from '../../models/fridge.models';


@Component({
  selector: 'app-list-fridges-component',
  imports: [
    CommonModule,
    FormsModule,
    DatePipe,
  ],
  templateUrl: './list-fridges-component.html',
  styleUrl: './list-fridges-component.css',
})
export class ListFridgesComponent implements OnInit {
  
  fridges: FridgeDetailsApi[] = [];

  page = 0;
  size = 10;
  totalPages = 0;

  filterName: string = '';

  sortField: string = 'name';
  sortDirection: 'asc' | 'desc' = 'asc';

  newFridgeName: string = '';
  errorMessages: string[] = [];
  creationSuccess: boolean | null = null;

  deleteErrorMessages: string[] = [];
  deleteSuccess: boolean | null = null;

  constructor(private fridgeService: FridgeService, private router: Router) {}

  ngOnInit() {
    this.loadFridges();
  }

  loadFridges() {
    const sortParam = [`${this.sortField}-${this.sortDirection}`];

    this.fridgeService
      .listFridges(this.page, this.size, this.filterName, sortParam)
      .subscribe(response => {
        this.fridges = response.content;
        this.totalPages = response.totalPages;
      });
  }

  nextPage() {
    if (this.page + 1 < this.totalPages) {
      this.page++;
      this.loadFridges();
    }
  }

  previousPage() {
    if (this.page > 0) {
      this.page--;
      this.loadFridges();
    }
  }

  changeSort(field: string) {
    if (this.sortField === field) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = field;
      this.sortDirection = 'asc';
    }

    this.loadFridges();
  }

  applyFilter() {
    this.page = 0; 
    this.loadFridges();
  }

  createFridge() {
    this.errorMessages = [];
    this.creationSuccess = null;
  
    if (!this.newFridgeName || this.newFridgeName.trim().length === 0) {
      this.errorMessages = ["Fridge name is required"];
      return;
    }
  
    this.fridgeService.createFridge(this.newFridgeName.trim())
      .subscribe({
        next: (response) => {
          this.creationSuccess = response.success;
  
          if (response.success) {
            this.newFridgeName = '';
            this.loadFridges(); // refresh list
          }
        },
        error: (err) => {
          if (err.error?.messages) {
            this.errorMessages = err.error.messages.map((m: any) => m.label);
          } else {
            this.errorMessages = ["An unexpected error occurred"];
          }
        }
      });
  }

  deleteFridge(id: number, name: string) {
    this.deleteErrorMessages = [];
    this.deleteSuccess = null;
  
    const confirmed = confirm(`Are you sure you want to delete fridge: "${name}"?`);
    if (!confirmed) {
      return;
    }
  
    this.fridgeService.deleteFridge(id).subscribe({
      next: (resp) => {
        this.deleteSuccess = resp.success;
  
        if (resp.success) {
          this.loadFridges(); // refresh list
        }
      },
      error: (err) => {
        if (err.error?.messages) {
          this.deleteErrorMessages = err.error.messages.map((m: any) => m.label);
        } else {
          this.deleteErrorMessages = ['An unexpected error occurred while deleting the fridge.'];
        }
      }
    });
  }

  viewItems(fridgeId: number) {
    this.router.navigate(['/fridges', fridgeId, 'items']);
  }

  viewRecipes(fridgeId: number) {
    this.router.navigate(['/fridges', fridgeId, 'recipes']);
  }
  
}
