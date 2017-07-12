import { Cours } from '../cours';
export class Filiere {
    constructor(
        public id?: number,
        public code?: string,
        public intitule?: string,
        public cours?: Cours,
    ) {
    }
}
