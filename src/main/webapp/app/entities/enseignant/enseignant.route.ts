import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { EnseignantComponent } from './enseignant.component';
import { EnseignantDetailComponent } from './enseignant-detail.component';
import { EnseignantPopupComponent } from './enseignant-dialog.component';
import { EnseignantDeletePopupComponent } from './enseignant-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class EnseignantResolvePagingParams implements Resolve<any> {

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

export const enseignantRoute: Routes = [
    {
        path: 'enseignant',
        component: EnseignantComponent,
        resolve: {
            'pagingParams': EnseignantResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.enseignant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'enseignant/:id',
        component: EnseignantDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.enseignant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const enseignantPopupRoute: Routes = [
    {
        path: 'enseignant-new',
        component: EnseignantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.enseignant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'enseignant/:id/edit',
        component: EnseignantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.enseignant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'enseignant/:id/delete',
        component: EnseignantDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.enseignant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
