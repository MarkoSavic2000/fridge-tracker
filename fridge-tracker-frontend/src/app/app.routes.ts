import { Routes } from '@angular/router';
import { ListFridgesComponent } from './components/list-fridges-component/list-fridges-component';
import { AuthGuard } from './auth.guard';
import { FridgeItemsComponent } from './components/fridge-items-component/fridge-items-component';
import { FridgeRecipesComponent } from './components/fridge-recipes-component/fridge-recipes-component';

export const routes: Routes = [
    {
      path: '',
      redirectTo: 'fridges',
      pathMatch: 'full'
    },
    {
      path: 'fridges',
      component: ListFridgesComponent,
      canActivate: [AuthGuard]
    },
    {
      path: 'fridges/:id/items',
      component: FridgeItemsComponent,
      canActivate: [AuthGuard]
    },
    {
      path: 'fridges/:id/recipes',
      component: FridgeRecipesComponent,
      canActivate: [AuthGuard]
    }
      
  ];
