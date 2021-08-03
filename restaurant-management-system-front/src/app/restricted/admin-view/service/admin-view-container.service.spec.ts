import { TestBed } from '@angular/core/testing';

import { AdminViewContainerService } from './admin-view-container.service';

describe('AdminViewContainerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AdminViewContainerService = TestBed.get(AdminViewContainerService);
    expect(service).toBeTruthy();
  });
});
