import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AggiornaProdottoComponent } from './aggiorna-prodotto.component';

describe('AggiornaProdottoComponent', () => {
  let component: AggiornaProdottoComponent;
  let fixture: ComponentFixture<AggiornaProdottoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AggiornaProdottoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AggiornaProdottoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
