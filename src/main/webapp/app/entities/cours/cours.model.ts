import { Enseignant } from '../enseignant';
import { Filiere } from '../filiere';
export class Cours {
    constructor(
        public id?: number,
        public codeUE?: string,
        public intituleUE?: string,
        public semestre?: string,
        public duree?: string,
        public enseignant?: Enseignant,
        public filiere?: Filiere,
    ) {
    }
}
