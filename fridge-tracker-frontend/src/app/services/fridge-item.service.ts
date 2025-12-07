import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { FridgeItemPage } from '../models/fridge-item.models';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class FridgeItemService {

  private readonly baseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  getFridgeItems(
    fridgeId: number,
    page: number,
    size: number,
    filters: any,
    sort: string[]
  ): Observable<FridgeItemPage> {

    let params = new HttpParams()
      .set('page', page)
      .set('size', size);

    if (filters.name) params = params.set('name', filters.name);
    if (filters.category) params = params.set('category', filters.category);
    if (filters.storedOnFrom) params = params.set('storedOnFrom', filters.storedOnFrom);
    if (filters.storedOnTo) params = params.set('storedOnTo', filters.storedOnTo);
    if (filters.expiresOnFrom) params = params.set('expiresOnFrom', filters.expiresOnFrom);
    if (filters.expiresOnTo) params = params.set('expiresOnTo', filters.expiresOnTo);
    if (filters.onlyExpired !== null) params = params.set('onlyExpired', filters.onlyExpired);

    sort.forEach(s => params = params.append('sort', s));

    return this.http.get<FridgeItemPage>(`${this.baseUrl}/fridges/${fridgeId}/items`, {
      params
    });
  }

  deleteFridgeItem(itemId: number) {
    return this.http.delete<{ success: boolean }>(`${this.baseUrl}/fridge-items/${itemId}`);
  }

  addFridgeItem(fridgeId: number, body: any) {
    return this.http.post<{ success: boolean }>(
      `${this.baseUrl}/fridges/${fridgeId}/items`,
      body
    );
  }

  takeFridgeItem(itemId: number, quantity: number) {
    return this.http.patch<{ success: boolean }>(
      `${this.baseUrl}/fridge-items/${itemId}/take`,
      { quantity }
    );
  }
  
  
}
