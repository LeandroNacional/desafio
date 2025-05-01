import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalheCafeComponent } from './detalhe-cafe.component';

describe('DetalheCafeComponent', () => {
  let component: DetalheCafeComponent;
  let fixture: ComponentFixture<DetalheCafeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalheCafeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalheCafeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
