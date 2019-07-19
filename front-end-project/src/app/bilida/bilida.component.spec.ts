import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BilidaComponent } from './bilida.component';

describe('BilidaComponent', () => {
  let component: BilidaComponent;
  let fixture: ComponentFixture<BilidaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BilidaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BilidaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
