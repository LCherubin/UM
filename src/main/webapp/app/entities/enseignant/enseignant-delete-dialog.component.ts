import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Enseignant } from './enseignant.model';
import { EnseignantPopupService } from './enseignant-popup.service';
import { EnseignantService } from './enseignant.service';

@Component({
    selector: 'jhi-enseignant-delete-dialog',
    templateUrl: './enseignant-delete-dialog.component.html'
})
export class EnseignantDeleteDialogComponent {

    enseignant: Enseignant;

    constructor(
        private enseignantService: EnseignantService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.enseignantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'enseignantListModification',
                content: 'Deleted an enseignant'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('mathFinEcoUniversityApp.enseignant.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-enseignant-delete-popup',
    template: ''
})
export class EnseignantDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private enseignantPopupService: EnseignantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.enseignantPopupService
                .open(EnseignantDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
