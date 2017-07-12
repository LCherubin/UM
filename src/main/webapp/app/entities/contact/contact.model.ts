import { Country } from '../country';
export class Contact {
    constructor(
        public id?: number,
        public ville?: string,
        public codePostal?: string,
        public telephone?: string,
        public email?: string,
        public country?: Country,
    ) {
    }
}
