
const enum Niveau {
    'PREPA',
    'LICENCE',
    'MASTER'

};
import { AnneeUniversitaire } from '../annee-universitaire';
import { Etudiant } from '../etudiant';
export class Contribution {
    constructor(
        public id?: number,
        public niveau?: Niveau,
        public fraisInsciprion?: number,
        public montantTotal?: number,
        public montantTranche1?: number,
        public montantTranche2?: number,
        public montantPayer1?: number,
        public montantPayer2?: number,
        public totalPayer?: number,
        public datePayment?: any,
        public annees?: AnneeUniversitaire,
        public etudiant?: Etudiant,
    ) {
    }
}
