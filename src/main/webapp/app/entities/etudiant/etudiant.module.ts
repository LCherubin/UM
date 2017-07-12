import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MathFinEcoUniversitySharedModule } from '../../shared';
import {
    EtudiantService,
    EtudiantPopupService,
    EtudiantComponent,
    EtudiantDetailComponent,
    EtudiantDialogComponent,
    EtudiantPopupComponent,
    EtudiantDeletePopupComponent,
    EtudiantDeleteDialogComponent,
    etudiantRoute,
    etudiantPopupRoute,
    EtudiantResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...etudiantRoute,
    ...etudiantPopupRoute,
];

@NgModule({
    imports: [
        MathFinEcoUniversitySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EtudiantComponent,
        EtudiantDetailComponent,
        EtudiantDialogComponent,
        EtudiantDeleteDialogComponent,
        EtudiantPopupComponent,
        EtudiantDeletePopupComponent,
    ],
    entryComponents: [
        EtudiantComponent,
        EtudiantDialogComponent,
        EtudiantPopupComponent,
        EtudiantDeleteDialogComponent,
        EtudiantDeletePopupComponent,
    ],
    providers: [
        EtudiantService,
        EtudiantPopupService,
        EtudiantResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MathFinEcoUniversityEtudiantModule {}
