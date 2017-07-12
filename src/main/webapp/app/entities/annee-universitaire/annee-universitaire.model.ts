import { Etudiant } from '../etudiant';
import { Enseignant } from '../enseignant';
export class AnneeUniversitaire {
    constructor(
        public id?: number,
        public annees?: string,
        public etudiant?: Etudiant,
        public enseignant?: Enseignant,
    ) {
    }
}
