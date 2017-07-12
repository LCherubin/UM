import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Contribution } from './contribution.model';
import { ContributionPopupService } from './contribution-popup.service';
import { ContributionService } from './contribution.service';
import { AnneeUniversitaire, AnneeUniversitaireService } from '../annee-universitaire';
import { Etudiant, EtudiantService } from '../etudiant';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-contribution-dialog',
    templateUrl: './contribution-dialog.component.html'
})
export class ContributionDialogComponent implements OnInit {

    contribution: Contribution;
    authorities: any[];
    isSaving: boolean;

    annees: AnneeUniversitaire[];

    etudiants: Etudiant[];
    datePaymentDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private contributionService: ContributionService,
        private anneeUniversitaireService: AnneeUniversitaireService,
        private etudiantService: EtudiantService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.anneeUniversitaireService
            .query({filter: 'contribution-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.contribution.annees || !this.contribution.annees.id) {
                    this.annees = res.json;
                } else {
                    this.anneeUniversitaireService
                        .find(this.contribution.annees.id)
                        .subscribe((subRes: AnneeUniversitaire) => {
                            this.annees = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.etudiantService.query()
            .subscribe((res: ResponseWrapper) => { this.etudiants = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.contribution.id !== undefined) {
            this.subscribeToSaveResponse(
                this.contributionService.update(this.contribution), false);
        } else {
            this.subscribeToSaveResponse(
                this.contributionService.create(this.contribution), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Contribution>, isCreated: boolean) {
        result.subscribe((res: Contribution) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Contribution, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'mathFinEcoUniversityApp.contribution.created'
            : 'mathFinEcoUniversityApp.contribution.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'contributionListModification', content: 'OK'});
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

    trackAnneeUniversitaireById(index: number, item: AnneeUniversitaire) {
        return item.id;
    }

    trackEtudiantById(index: number, item: Etudiant) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-contribution-popup',
    template: ''
})
export class ContributionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contributionPopupService: ContributionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.contributionPopupService
                    .open(ContributionDialogComponent, params['id']);
            } else {
                this.modalRef = this.contributionPopupService
                    .open(ContributionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
