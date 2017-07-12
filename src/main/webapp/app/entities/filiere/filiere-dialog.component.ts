import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Filiere } from './filiere.model';
import { FilierePopupService } from './filiere-popup.service';
import { FiliereService } from './filiere.service';

@Component({
    selector: 'jhi-filiere-dialog',
    templateUrl: './filiere-dialog.component.html'
})
export class FiliereDialogComponent implements OnInit {

    filiere: Filiere;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private filiereService: FiliereService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.filiere.id !== undefined) {
            this.subscribeToSaveResponse(
                this.filiereService.update(this.filiere), false);
        } else {
            this.subscribeToSaveResponse(
                this.filiereService.create(this.filiere), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Filiere>, isCreated: boolean) {
        result.subscribe((res: Filiere) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Filiere, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'mathFinEcoUniversityApp.filiere.created'
            : 'mathFinEcoUniversityApp.filiere.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'filiereListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-filiere-popup',
    template: ''
})
export class FilierePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private filierePopupService: FilierePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.filierePopupService
                    .open(FiliereDialogComponent, params['id']);
            } else {
                this.modalRef = this.filierePopupService
                    .open(FiliereDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
