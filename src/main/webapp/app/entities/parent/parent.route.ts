import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ParentComponent } from './parent.component';
import { ParentDetailComponent } from './parent-detail.component';
import { ParentPopupComponent } from './parent-dialog.component';
import { ParentDeletePopupComponent } from './parent-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ParentResolvePagingParams implements Resolve<any> {

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

export const parentRoute: Routes = [
    {
        path: 'parent',
        component: ParentComponent,
        resolve: {
            'pagingParams': ParentResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.parent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'parent/:id',
        component: ParentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.parent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const parentPopupRoute: Routes = [
    {
        path: 'parent-new',
        component: ParentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.parent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'parent/:id/edit',
        component: ParentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.parent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'parent/:id/delete',
        component: ParentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.parent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
