import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ContributionComponent } from './contribution.component';
import { ContributionDetailComponent } from './contribution-detail.component';
import { ContributionPopupComponent } from './contribution-dialog.component';
import { ContributionDeletePopupComponent } from './contribution-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ContributionResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: PaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const contributionRoute: Routes = [
    {
        path: 'contribution',
        component: ContributionComponent,
        resolve: {
            'pagingParams': ContributionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.contribution.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'contribution/:id',
        component: ContributionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.contribution.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contributionPopupRoute: Routes = [
    {
        path: 'contribution-new',
        component: ContributionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.contribution.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contribution/:id/edit',
        component: ContributionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.contribution.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contribution/:id/delete',
        component: ContributionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.contribution.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
