import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MathFinEcoUniversitySharedModule } from '../../shared';
import {
    EnseignantService,
    EnseignantPopupService,
    EnseignantComponent,
    EnseignantDetailComponent,
    EnseignantDialogComponent,
    EnseignantPopupComponent,
    EnseignantDeletePopupComponent,
    EnseignantDeleteDialogComponent,
    enseignantRoute,
    enseignantPopupRoute,
    EnseignantResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...enseignantRoute,
    ...enseignantPopupRoute,
];

@NgModule({
    imports: [
        MathFinEcoUniversitySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EnseignantComponent,
        EnseignantDetailComponent,
        EnseignantDialogComponent,
        EnseignantDeleteDialogComponent,
        EnseignantPopupComponent,
        EnseignantDeletePopupComponent,
    ],
    entryComponents: [
        EnseignantComponent,
        EnseignantDialogComponent,
        EnseignantPopupComponent,
        EnseignantDeleteDialogComponent,
        EnseignantDeletePopupComponent,
    ],
    providers: [
        EnseignantService,
        EnseignantPopupService,
        EnseignantResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MathFinEcoUniversityEnseignantModule {}
