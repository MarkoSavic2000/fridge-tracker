import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GetRecipesResponse } from '../models/recipe.models';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class RecipeService {

  private readonly baseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  getRecipes(fridgeId: number): Observable<GetRecipesResponse> {
    return this.http.get<GetRecipesResponse>(`${this.baseUrl}/fridges/${fridgeId}/recipes`);
  }
}
