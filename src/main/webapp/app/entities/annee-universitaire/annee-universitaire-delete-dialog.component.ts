import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { AnneeUniversitaire } from './annee-universitaire.model';
import { AnneeUniversitairePopupService } from './annee-universitaire-popup.service';
import { AnneeUniversitaireService } from './annee-universitaire.service';

@Component({
    selector: 'jhi-annee-universitaire-delete-dialog',
    templateUrl: './annee-universitaire-delete-dialog.component.html'
})
export class AnneeUniversitaireDeleteDialogComponent {

    anneeUniversitaire: AnneeUniversitaire;

    constructor(
        private anneeUniversitaireService: AnneeUniversitaireService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.anneeUniversitaireService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'anneeUniversitaireListModification',
                content: 'Deleted an anneeUniversitaire'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('mathFinEcoUniversityApp.anneeUniversitaire.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-annee-universitaire-delete-popup',
    template: ''
})
export class AnneeUniversitaireDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private anneeUniversitairePopupService: AnneeUniversitairePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.anneeUniversitairePopupService
                .open(AnneeUniversitaireDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
