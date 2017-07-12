
const enum Sexe {
    'MASCULIN',
    'FEMININ'

};

const enum Niveau {
    'PREPA 1',
    'PREPA 2',
    'LICENCE 1',
    'LICENCE 2',
    'LICENCE 3',
    'MASTER 1',
    'MASTER 2',
    'ING ECAM-EPMI 1"',
    'ING ECAM-EPMI 2'
};

const enum Statut {
    'SPONSORING',
    'DEMI BOURSIER',
    'BOURSIER'

};

const enum StatInscription {
    'PREINSCRIPTION',
    'EN COURS',
    'VALIDE'

};
import { Contact } from '../contact';
import { Filiere } from '../filiere';
import { AnneeUniversitaire } from '../annee-universitaire';
import { Contribution } from '../contribution';
import { Parent } from '../parent';
export class Etudiant {
    constructor(
        public id?: number,
        public matricule?: number,
        public prenoms?: string,
        public nom?: string,
        public sexe?: Sexe,
        public dateNaissance?: any,
        public lieuNaisance?: string,
        public promotion?: string,
        public niveau?: Niveau,
        public dateInscription?: any,
        public statut?: Statut,
        public statutInscription?: StatInscription,
        public contact?: Contact,
        public filier?: Filiere,
        public annees?: AnneeUniversitaire,
        public contribution?: Contribution,
        public parent?: Parent,
    ) {
    }
}
