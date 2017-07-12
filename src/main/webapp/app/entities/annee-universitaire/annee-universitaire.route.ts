import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AnneeUniversitaireComponent } from './annee-universitaire.component';
import { AnneeUniversitaireDetailComponent } from './annee-universitaire-detail.component';
import { AnneeUniversitairePopupComponent } from './annee-universitaire-dialog.component';
import { AnneeUniversitaireDeletePopupComponent } from './annee-universitaire-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class AnneeUniversitaireResolvePagingParams implements Resolve<any> {

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

export const anneeUniversitaireRoute: Routes = [
    {
        path: 'annee-universitaire',
        component: AnneeUniversitaireComponent,
        resolve: {
            'pagingParams': AnneeUniversitaireResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.anneeUniversitaire.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'annee-universitaire/:id',
        component: AnneeUniversitaireDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.anneeUniversitaire.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const anneeUniversitairePopupRoute: Routes = [
    {
        path: 'annee-universitaire-new',
        component: AnneeUniversitairePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.anneeUniversitaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'annee-universitaire/:id/edit',
        component: AnneeUniversitairePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.anneeUniversitaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'annee-universitaire/:id/delete',
        component: AnneeUniversitaireDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.anneeUniversitaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
