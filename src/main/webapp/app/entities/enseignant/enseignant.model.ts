
const enum Sexe {
    'MASCULIN',
    'FEMININ'

};
import { Contact } from '../contact';
import { Cours } from '../cours';
import { AnneeUniversitaire } from '../annee-universitaire';
export class Enseignant {
    constructor(
        public id?: number,
        public prenoms?: string,
        public nom?: string,
        public sexe?: Sexe,
        public dateNaissance?: any,
        public lieuNaisance?: string,
        public titre?: string,
        public dateEnregistrement?: any,
        public contact?: Contact,
        public cours?: Cours,
        public annees?: AnneeUniversitaire,
    ) {
    }
}
