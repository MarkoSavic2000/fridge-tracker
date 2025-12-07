import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FridgePage } from '../models/fridge.models';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class FridgeService {

  private readonly baseUrl = `${environment.apiBaseUrl}/fridges`;

  constructor(private http: HttpClient) {}

  listFridges(
    page: number = 0,
    size: number = 10,
    name?: string,
    sort: string[] = []
  ): Observable<FridgePage> {
  
    let params = new HttpParams()
      .set('page', page)
      .set('size', size);
  
    if (name) params = params.set('name', name);
  
    sort.forEach(s => {
      params = params.append('sort', s); 
    });
  
    return this.http.get<FridgePage>(this.baseUrl, { params });
  }

  createFridge(name: string) {
    return this.http.post<{ success: boolean }>(
      this.baseUrl,
      { name }
    );
  }

  deleteFridge(id: number) {
    return this.http.delete<{ success: boolean }>(`${this.baseUrl}/${id}`);
  }
  
}
