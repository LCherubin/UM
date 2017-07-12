import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Etudiant } from './etudiant.model';
import { EtudiantPopupService } from './etudiant-popup.service';
import { EtudiantService } from './etudiant.service';
import { Contact, ContactService,ContactDialogComponent } from '../contact';
import { Filiere, FiliereService } from '../filiere';
import { Parent, ParentService } from '../parent';
import { ResponseWrapper } from '../../shared';



@Component({
    selector: 'jhi-etudiant-dialog',
    templateUrl: './etudiant-dialog.component.html'
})
export class EtudiantDialogComponent implements OnInit {

    etudiant: Etudiant;
    authorities: any[];
    isSaving: boolean;

    contacts: Contact[];

    filiers: Filiere[];

    parents: Parent[];
    dateNaissanceDp: any;
    dateInscriptionDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private etudiantService: EtudiantService,
        private contactService: ContactService,
        private filiereService: FiliereService,
        private parentService: ParentService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.contactService
            .query({filter: 'etudiant-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.etudiant.contact || !this.etudiant.contact.id) {
                    this.contacts = res.json;
                } else {
                    this.contactService
                        .find(this.etudiant.contact.id)
                        .subscribe((subRes: Contact) => {
                            this.contacts = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.filiereService
            .query({filter: 'etudiant-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.etudiant.filier || !this.etudiant.filier.id) {
                    this.filiers = res.json;
                } else {
                    this.filiereService
                        .find(this.etudiant.filier.id)
                        .subscribe((subRes: Filiere) => {
                            this.filiers = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.parentService.query()
            .subscribe((res: ResponseWrapper) => { this.parents = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.etudiant.id !== undefined) {
            this.subscribeToSaveResponse(
                this.etudiantService.update(this.etudiant), false);
        } else {
            this.subscribeToSaveResponse(
                this.etudiantService.create(this.etudiant), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Etudiant>, isCreated: boolean) {
        result.subscribe((res: Etudiant) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Etudiant, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'mathFinEcoUniversityApp.etudiant.created'
            : 'mathFinEcoUniversityApp.etudiant.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'etudiantListModification', content: 'OK'});
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

    trackFiliereById(index: number, item: Filiere) {
        return item.id;
    }

    trackParentById(index: number, item: Parent) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-etudiant-popup',
    template: ''
})
export class EtudiantPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private etudiantPopupService: EtudiantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.etudiantPopupService
                    .open(EtudiantDialogComponent, params['id']);
            } else {
                this.modalRef = this.etudiantPopupService
                    .open(EtudiantDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
