import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPorudzbineComponent } from './add-porudzbine.component';

describe('AddPorudzbineComponent', () => {
  let component: AddPorudzbineComponent;
  let fixture: ComponentFixture<AddPorudzbineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddPorudzbineComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddPorudzbineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
