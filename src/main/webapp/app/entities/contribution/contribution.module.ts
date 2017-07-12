import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MathFinEcoUniversitySharedModule } from '../../shared';
import {
    ContributionService,
    ContributionPopupService,
    ContributionComponent,
    ContributionDetailComponent,
    ContributionDialogComponent,
    ContributionPopupComponent,
    ContributionDeletePopupComponent,
    ContributionDeleteDialogComponent,
    contributionRoute,
    contributionPopupRoute,
    ContributionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...contributionRoute,
    ...contributionPopupRoute,
];

@NgModule({
    imports: [
        MathFinEcoUniversitySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ContributionComponent,
        ContributionDetailComponent,
        ContributionDialogComponent,
        ContributionDeleteDialogComponent,
        ContributionPopupComponent,
        ContributionDeletePopupComponent,
    ],
    entryComponents: [
        ContributionComponent,
        ContributionDialogComponent,
        ContributionPopupComponent,
        ContributionDeleteDialogComponent,
        ContributionDeletePopupComponent,
    ],
    providers: [
        ContributionService,
        ContributionPopupService,
        ContributionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MathFinEcoUniversityContributionModule {}
