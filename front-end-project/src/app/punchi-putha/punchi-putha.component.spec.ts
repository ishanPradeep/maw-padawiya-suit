import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PunchiPuthaComponent } from './punchi-putha.component';

describe('PunchiPuthaComponent', () => {
  let component: PunchiPuthaComponent;
  let fixture: ComponentFixture<PunchiPuthaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PunchiPuthaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PunchiPuthaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
