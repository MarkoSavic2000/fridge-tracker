import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FridgeRecipesComponent } from './fridge-recipes-component';

describe('FridgeRecipesComponent', () => {
  let component: FridgeRecipesComponent;
  let fixture: ComponentFixture<FridgeRecipesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FridgeRecipesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FridgeRecipesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
