import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MathFinEcoUniversitySharedModule } from '../../shared';
import {
    ParentService,
    ParentPopupService,
    ParentComponent,
    ParentDetailComponent,
    ParentDialogComponent,
    ParentPopupComponent,
    ParentDeletePopupComponent,
    ParentDeleteDialogComponent,
    parentRoute,
    parentPopupRoute,
    ParentResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...parentRoute,
    ...parentPopupRoute,
];

@NgModule({
    imports: [
        MathFinEcoUniversitySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ParentComponent,
        ParentDetailComponent,
        ParentDialogComponent,
        ParentDeleteDialogComponent,
        ParentPopupComponent,
        ParentDeletePopupComponent,
    ],
    entryComponents: [
        ParentComponent,
        ParentDialogComponent,
        ParentPopupComponent,
        ParentDeleteDialogComponent,
        ParentDeletePopupComponent,
    ],
    providers: [
        ParentService,
        ParentPopupService,
        ParentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MathFinEcoUniversityParentModule {}
