import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddKorisnikaComponent } from './add-korisnika.component';

describe('AddKorisnikaComponent', () => {
  let component: AddKorisnikaComponent;
  let fixture: ComponentFixture<AddKorisnikaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddKorisnikaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddKorisnikaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
