import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MathFinEcoUniversitySharedModule } from '../../shared';
import {
    AnneeUniversitaireService,
    AnneeUniversitairePopupService,
    AnneeUniversitaireComponent,
    AnneeUniversitaireDetailComponent,
    AnneeUniversitaireDialogComponent,
    AnneeUniversitairePopupComponent,
    AnneeUniversitaireDeletePopupComponent,
    AnneeUniversitaireDeleteDialogComponent,
    anneeUniversitaireRoute,
    anneeUniversitairePopupRoute,
    AnneeUniversitaireResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...anneeUniversitaireRoute,
    ...anneeUniversitairePopupRoute,
];

@NgModule({
    imports: [
        MathFinEcoUniversitySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AnneeUniversitaireComponent,
        AnneeUniversitaireDetailComponent,
        AnneeUniversitaireDialogComponent,
        AnneeUniversitaireDeleteDialogComponent,
        AnneeUniversitairePopupComponent,
        AnneeUniversitaireDeletePopupComponent,
    ],
    entryComponents: [
        AnneeUniversitaireComponent,
        AnneeUniversitaireDialogComponent,
        AnneeUniversitairePopupComponent,
        AnneeUniversitaireDeleteDialogComponent,
        AnneeUniversitaireDeletePopupComponent,
    ],
    providers: [
        AnneeUniversitaireService,
        AnneeUniversitairePopupService,
        AnneeUniversitaireResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MathFinEcoUniversityAnneeUniversitaireModule {}
