import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Etudiant } from './etudiant.model';
import { EtudiantPopupService } from './etudiant-popup.service';
import { EtudiantService } from './etudiant.service';

@Component({
    selector: 'jhi-etudiant-delete-dialog',
    templateUrl: './etudiant-delete-dialog.component.html'
})
export class EtudiantDeleteDialogComponent {

    etudiant: Etudiant;

    constructor(
        private etudiantService: EtudiantService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.etudiantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'etudiantListModification',
                content: 'Deleted an etudiant'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('mathFinEcoUniversityApp.etudiant.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-etudiant-delete-popup',
    template: ''
})
export class EtudiantDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private etudiantPopupService: EtudiantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.etudiantPopupService
                .open(EtudiantDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
