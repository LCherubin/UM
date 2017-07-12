import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { AnneeUniversitaire } from './annee-universitaire.model';
import { AnneeUniversitairePopupService } from './annee-universitaire-popup.service';
import { AnneeUniversitaireService } from './annee-universitaire.service';
import { Etudiant, EtudiantService } from '../etudiant';
import { Enseignant, EnseignantService } from '../enseignant';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-annee-universitaire-dialog',
    templateUrl: './annee-universitaire-dialog.component.html'
})
export class AnneeUniversitaireDialogComponent implements OnInit {

    anneeUniversitaire: AnneeUniversitaire;
    authorities: any[];
    isSaving: boolean;

    etudiants: Etudiant[];

    enseignants: Enseignant[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private anneeUniversitaireService: AnneeUniversitaireService,
        private etudiantService: EtudiantService,
        private enseignantService: EnseignantService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.etudiantService.query()
            .subscribe((res: ResponseWrapper) => { this.etudiants = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.enseignantService.query()
            .subscribe((res: ResponseWrapper) => { this.enseignants = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.anneeUniversitaire.id !== undefined) {
            this.subscribeToSaveResponse(
                this.anneeUniversitaireService.update(this.anneeUniversitaire), false);
        } else {
            this.subscribeToSaveResponse(
                this.anneeUniversitaireService.create(this.anneeUniversitaire), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<AnneeUniversitaire>, isCreated: boolean) {
        result.subscribe((res: AnneeUniversitaire) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: AnneeUniversitaire, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'mathFinEcoUniversityApp.anneeUniversitaire.created'
            : 'mathFinEcoUniversityApp.anneeUniversitaire.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'anneeUniversitaireListModification', content: 'OK'});
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

    trackEtudiantById(index: number, item: Etudiant) {
        return item.id;
    }

    trackEnseignantById(index: number, item: Enseignant) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-annee-universitaire-popup',
    template: ''
})
export class AnneeUniversitairePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private anneeUniversitairePopupService: AnneeUniversitairePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.anneeUniversitairePopupService
                    .open(AnneeUniversitaireDialogComponent, params['id']);
            } else {
                this.modalRef = this.anneeUniversitairePopupService
                    .open(AnneeUniversitaireDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
