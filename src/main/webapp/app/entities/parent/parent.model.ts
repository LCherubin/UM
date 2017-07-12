
const enum Sexe {
    'MASCULIN',
    'FEMININ'

};
import { Contact } from '../contact';
import { Etudiant } from '../etudiant';
export class Parent {
    constructor(
        public id?: number,
        public prenoms?: string,
        public nom?: string,
        public sexe?: Sexe,
        public lien?: string,
        public contact?: Contact,
        public parent?: Etudiant,
    ) {
    }
}
