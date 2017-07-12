import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CoursComponent } from './cours.component';
import { CoursDetailComponent } from './cours-detail.component';
import { CoursPopupComponent } from './cours-dialog.component';
import { CoursDeletePopupComponent } from './cours-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CoursResolvePagingParams implements Resolve<any> {

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

export const coursRoute: Routes = [
    {
        path: 'cours',
        component: CoursComponent,
        resolve: {
            'pagingParams': CoursResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.cours.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cours/:id',
        component: CoursDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.cours.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const coursPopupRoute: Routes = [
    {
        path: 'cours-new',
        component: CoursPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.cours.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cours/:id/edit',
        component: CoursPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.cours.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cours/:id/delete',
        component: CoursDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.cours.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
