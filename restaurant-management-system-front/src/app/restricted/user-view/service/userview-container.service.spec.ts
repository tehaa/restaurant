import { TestBed } from '@angular/core/testing';

import { UserviewContainerService } from './userview-container.service';

describe('UserviewContainerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UserviewContainerService = TestBed.get(UserviewContainerService);
    expect(service).toBeTruthy();
  });
});
