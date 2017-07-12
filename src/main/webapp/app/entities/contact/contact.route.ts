import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ContactComponent } from './contact.component';
import { ContactDetailComponent } from './contact-detail.component';
import { ContactPopupComponent } from './contact-dialog.component';
import { ContactDeletePopupComponent } from './contact-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ContactResolvePagingParams implements Resolve<any> {

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

export const contactRoute: Routes = [
    {
        path: 'contact',
        component: ContactComponent,
        resolve: {
            'pagingParams': ContactResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.contact.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'contact/:id',
        component: ContactDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.contact.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contactPopupRoute: Routes = [
    {
        path: 'contact-new',
        component: ContactPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.contact.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contact/:id/edit',
        component: ContactPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.contact.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contact/:id/delete',
        component: ContactDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mathFinEcoUniversityApp.contact.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
