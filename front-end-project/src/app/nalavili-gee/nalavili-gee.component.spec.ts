import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NalaviliGeeComponent } from './nalavili-gee.component';

describe('NalaviliGeeComponent', () => {
  let component: NalaviliGeeComponent;
  let fixture: ComponentFixture<NalaviliGeeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NalaviliGeeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NalaviliGeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
