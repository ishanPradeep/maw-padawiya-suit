import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PurwaGarbaniComponent } from './purwa-garbani.component';

describe('PurwaGarbaniComponent', () => {
  let component: PurwaGarbaniComponent;
  let fixture: ComponentFixture<PurwaGarbaniComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PurwaGarbaniComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PurwaGarbaniComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
