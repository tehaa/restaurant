import { TestBed } from '@angular/core/testing';

import { UserviewService } from './userview.service';

describe('UserviewService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UserviewService = TestBed.get(UserviewService);
    expect(service).toBeTruthy();
  });
});
