import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListFridgesComponent } from './list-fridges-component';

describe('ListFridgesComponent', () => {
  let component: ListFridgesComponent;
  let fixture: ComponentFixture<ListFridgesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListFridgesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListFridgesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
