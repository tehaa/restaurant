export class IUser {
  token: string;
  sub: string;
  role: string;
  constructor() {
    this.token = '';
    this.sub = '';
    this.role = 'anonymous';
  }
}
