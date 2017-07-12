import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Parent } from './parent.model';
import { ParentPopupService } from './parent-popup.service';
import { ParentService } from './parent.service';
import { Contact, ContactService } from '../contact';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-parent-dialog',
    templateUrl: './parent-dialog.component.html'
})
export class ParentDialogComponent implements OnInit {

    parent: Parent;
    authorities: any[];
    isSaving: boolean;

    contacts: Contact[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private parentService: ParentService,
        private contactService: ContactService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.contactService
            .query({filter: 'parent-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.parent.contact || !this.parent.contact.id) {
                    this.contacts = res.json;
                } else {
                    this.contactService
                        .find(this.parent.contact.id)
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
        if (this.parent.id !== undefined) {
            this.subscribeToSaveResponse(
                this.parentService.update(this.parent), false);
        } else {
            this.subscribeToSaveResponse(
                this.parentService.create(this.parent), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Parent>, isCreated: boolean) {
        result.subscribe((res: Parent) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Parent, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'mathFinEcoUniversityApp.parent.created'
            : 'mathFinEcoUniversityApp.parent.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'parentListModification', content: 'OK'});
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
    selector: 'jhi-parent-popup',
    template: ''
})
export class ParentPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parentPopupService: ParentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.parentPopupService
                    .open(ParentDialogComponent, params['id']);
            } else {
                this.modalRef = this.parentPopupService
                    .open(ParentDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
