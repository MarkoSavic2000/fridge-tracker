import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FridgeItemsComponent } from './fridge-items-component';

describe('FridgeItemsComponent', () => {
  let component: FridgeItemsComponent;
  let fixture: ComponentFixture<FridgeItemsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FridgeItemsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FridgeItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
