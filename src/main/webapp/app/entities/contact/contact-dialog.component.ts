import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Contact } from './contact.model';
import { ContactPopupService } from './contact-popup.service';
import { ContactService } from './contact.service';
import { Country, CountryService } from '../country';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-contact-dialog',
    templateUrl: './contact-dialog.component.html'
})
export class ContactDialogComponent implements OnInit {

    contact: Contact;
    authorities: any[];
    isSaving: boolean;

    countries: Country[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private contactService: ContactService,
        private countryService: CountryService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.countryService
            .query({filter: 'contact-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.contact.country || !this.contact.country.id) {
                    this.countries = res.json;
                } else {
                    this.countryService
                        .find(this.contact.country.id)
                        .subscribe((subRes: Country) => {
                            this.countries = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.contact.id !== undefined) {
            this.subscribeToSaveResponse(
                this.contactService.update(this.contact), false);
        } else {
            this.subscribeToSaveResponse(
                this.contactService.create(this.contact), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Contact>, isCreated: boolean) {
        result.subscribe((res: Contact) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Contact, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'mathFinEcoUniversityApp.contact.created'
            : 'mathFinEcoUniversityApp.contact.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'contactListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackCountryById(index: number, item: Country) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-contact-popup',
    template: ''
})
export class ContactPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contactPopupService: ContactPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.contactPopupService
                    .open(ContactDialogComponent, params['id']);
            } else {
                this.modalRef = this.contactPopupService
                    .open(ContactDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
