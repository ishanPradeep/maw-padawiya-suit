import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GarbaniComponent } from './garbani.component';

describe('GarbaniComponent', () => {
  let component: GarbaniComponent;
  let fixture: ComponentFixture<GarbaniComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GarbaniComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GarbaniComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
