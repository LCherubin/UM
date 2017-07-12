import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Enseignant } from './enseignant.model';
import { EnseignantPopupService } from './enseignant-popup.service';
import { EnseignantService } from './enseignant.service';
import { Contact, ContactService } from '../contact';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-enseignant-dialog',
    templateUrl: './enseignant-dialog.component.html'
})
export class EnseignantDialogComponent implements OnInit {

    enseignant: Enseignant;
    authorities: any[];
    isSaving: boolean;

    contacts: Contact[];
    dateNaissanceDp: any;
    dateEnregistrementDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private enseignantService: EnseignantService,
        private contactService: ContactService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.contactService
            .query({filter: 'enseignant-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.enseignant.contact || !this.enseignant.contact.id) {
                    this.contacts = res.json;
                } else {
                    this.contactService
                        .find(this.enseignant.contact.id)
                        .subscribe((subRes: Contact) => {
                            this.contacts = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.enseignant.id !== undefined) {
            this.subscribeToSaveResponse(
                this.enseignantService.update(this.enseignant), false);
        } else {
            this.subscribeToSaveResponse(
                this.enseignantService.create(this.enseignant), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Enseignant>, isCreated: boolean) {
        result.subscribe((res: Enseignant) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Enseignant, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'mathFinEcoUniversityApp.enseignant.created'
            : 'mathFinEcoUniversityApp.enseignant.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'enseignantListModification', content: 'OK'});
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

    trackContactById(index: number, item: Contact) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-enseignant-popup',
    template: ''
})
export class EnseignantPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private enseignantPopupService: EnseignantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.enseignantPopupService
                    .open(EnseignantDialogComponent, params['id']);
            } else {
                this.modalRef = this.enseignantPopupService
                    .open(EnseignantDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
