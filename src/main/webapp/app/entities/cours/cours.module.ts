import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MathFinEcoUniversitySharedModule } from '../../shared';
import {
    CoursService,
    CoursPopupService,
    CoursComponent,
    CoursDetailComponent,
    CoursDialogComponent,
    CoursPopupComponent,
    CoursDeletePopupComponent,
    CoursDeleteDialogComponent,
    coursRoute,
    coursPopupRoute,
    CoursResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...coursRoute,
    ...coursPopupRoute,
];

@NgModule({
    imports: [
        MathFinEcoUniversitySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CoursComponent,
        CoursDetailComponent,
        CoursDialogComponent,
        CoursDeleteDialogComponent,
        CoursPopupComponent,
        CoursDeletePopupComponent,
    ],
    entryComponents: [
        CoursComponent,
        CoursDialogComponent,
        CoursPopupComponent,
        CoursDeleteDialogComponent,
        CoursDeletePopupComponent,
    ],
    providers: [
        CoursService,
        CoursPopupService,
        CoursResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MathFinEcoUniversityCoursModule {}
