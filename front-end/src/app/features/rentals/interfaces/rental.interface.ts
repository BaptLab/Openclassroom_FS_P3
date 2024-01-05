export interface Rental {
  id: number;
  name: string;
  surface: number;
  price: number;
  pictureUrl: string;
  description: string;
  owner_id: number;
  creationDate: Date;
  updateDate: Date;
}
