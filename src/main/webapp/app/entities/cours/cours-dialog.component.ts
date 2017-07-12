import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Cours } from './cours.model';
import { CoursPopupService } from './cours-popup.service';
import { CoursService } from './cours.service';
import { Enseignant, EnseignantService } from '../enseignant';
import { Filiere, FiliereService } from '../filiere';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-cours-dialog',
    templateUrl: './cours-dialog.component.html'
})
export class CoursDialogComponent implements OnInit {

    cours: Cours;
    authorities: any[];
    isSaving: boolean;

    enseignants: Enseignant[];

    filieres: Filiere[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private coursService: CoursService,
        private enseignantService: EnseignantService,
        private filiereService: FiliereService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.enseignantService.query()
            .subscribe((res: ResponseWrapper) => { this.enseignants = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.filiereService.query()
            .subscribe((res: ResponseWrapper) => { this.filieres = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cours.id !== undefined) {
            this.subscribeToSaveResponse(
                this.coursService.update(this.cours), false);
        } else {
            this.subscribeToSaveResponse(
                this.coursService.create(this.cours), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Cours>, isCreated: boolean) {
        result.subscribe((res: Cours) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Cours, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'mathFinEcoUniversityApp.cours.created'
            : 'mathFinEcoUniversityApp.cours.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'coursListModification', content: 'OK'});
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

    trackEnseignantById(index: number, item: Enseignant) {
        return item.id;
    }

    trackFiliereById(index: number, item: Filiere) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-cours-popup',
    template: ''
})
export class CoursPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private coursPopupService: CoursPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.coursPopupService
                    .open(CoursDialogComponent, params['id']);
            } else {
                this.modalRef = this.coursPopupService
                    .open(CoursDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
