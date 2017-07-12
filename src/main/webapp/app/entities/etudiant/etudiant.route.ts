import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { EtudiantComponent } from './etudiant.component';
import { EtudiantDetailComponent } from './etudiant-detail.component';
import { EtudiantPopupComponent } from './etudiant-dialog.component';
import { EtudiantDeletePopupComponent } from './etudiant-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class EtudiantResolvePagingParams implements Resolve<any> {

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

export const etudiantRoute: Routes = [
    {
        path: 'etudiant',
        component: EtudiantComponent,
        resolve: {
            'pagingParams': EtudiantResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.etudiant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'etudiant/:id',
        component: EtudiantDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.etudiant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const etudiantPopupRoute: Routes = [
    {
        path: 'etudiant-new',
        component: EtudiantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.etudiant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'etudiant/:id/edit',
        component: EtudiantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.etudiant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'etudiant/:id/delete',
        component: EtudiantDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.etudiant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
