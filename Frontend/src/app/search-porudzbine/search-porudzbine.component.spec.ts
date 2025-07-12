import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchPorudzbineComponent } from './search-porudzbine.component';

describe('SearchPorudzbineComponent', () => {
  let component: SearchPorudzbineComponent;
  let fixture: ComponentFixture<SearchPorudzbineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SearchPorudzbineComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchPorudzbineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
