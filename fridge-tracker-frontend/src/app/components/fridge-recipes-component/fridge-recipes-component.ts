import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RecipeService } from '../../services/recipe.service';
import { RecipeApi } from '../../models/recipe.models';

@Component({
  selector: 'app-fridge-recipes',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './fridge-recipes-component.html',
  styleUrls: ['./fridge-recipes-component.css']
})
export class FridgeRecipesComponent implements OnInit {

  fridgeId!: number;
  recipes: RecipeApi[] = [];

  errorMessages: string[] = [];
  loading = false;

  constructor(
    private route: ActivatedRoute,
    private recipeService: RecipeService
  ) {}

  ngOnInit() {
    this.fridgeId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadRecipes();
  }

  loadRecipes() {
    this.loading = true;
    this.errorMessages = [];

    this.recipeService.getRecipes(this.fridgeId).subscribe({
      next: resp => {
        this.recipes = resp.recipes;
        this.loading = false;
      },
      error: err => {
        this.loading = false;

        if (err.error?.messages) {
          this.errorMessages = err.error.messages.map((m: any) => m.label);
        } else {
          this.errorMessages = ["Failed to load recipes."];
        }
      }
    });
  }
}