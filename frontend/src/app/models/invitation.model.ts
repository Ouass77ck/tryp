export interface Invitation {
  id: number;
  username: string;
  avatar: string;
  status: 'pending' | 'accepted' | 'rejected';
}