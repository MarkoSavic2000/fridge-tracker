export interface IngredientInfoApi {
    ingredient: string;
  }
  
  export interface RecipeApi {
    title: string;
    image: string;
    missingIngredients: IngredientInfoApi[];
    presentIngredients: IngredientInfoApi[];
  }
  
  export interface GetRecipesResponse {
    recipes: RecipeApi[];
  }
  