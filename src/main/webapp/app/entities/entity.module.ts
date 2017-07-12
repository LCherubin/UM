import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MathFinEcoUniversityCountryModule } from './country/country.module';
import { MathFinEcoUniversityContributionModule } from './contribution/contribution.module';
import { MathFinEcoUniversityAnneeUniversitaireModule } from './annee-universitaire/annee-universitaire.module';
import { MathFinEcoUniversityContactModule } from './contact/contact.module';
import { MathFinEcoUniversityEtudiantModule } from './etudiant/etudiant.module';
import { MathFinEcoUniversityEnseignantModule } from './enseignant/enseignant.module';
import { MathFinEcoUniversityParentModule } from './parent/parent.module';
import { MathFinEcoUniversityFiliereModule } from './filiere/filiere.module';
import { MathFinEcoUniversityCoursModule } from './cours/cours.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MathFinEcoUniversityCountryModule,
        MathFinEcoUniversityContributionModule,
        MathFinEcoUniversityAnneeUniversitaireModule,
        MathFinEcoUniversityContactModule,
        MathFinEcoUniversityEtudiantModule,
        MathFinEcoUniversityEnseignantModule,
        MathFinEcoUniversityParentModule,
        MathFinEcoUniversityFiliereModule,
        MathFinEcoUniversityCoursModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MathFinEcoUniversityEntityModule {}
