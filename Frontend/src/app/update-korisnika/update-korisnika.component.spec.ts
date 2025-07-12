import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateKorisnikaComponent } from './update-korisnika.component';

describe('UpdateKorisnikaComponent', () => {
  let component: UpdateKorisnikaComponent;
  let fixture: ComponentFixture<UpdateKorisnikaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateKorisnikaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateKorisnikaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
