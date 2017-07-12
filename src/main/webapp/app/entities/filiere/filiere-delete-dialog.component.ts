import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Filiere } from './filiere.model';
import { FilierePopupService } from './filiere-popup.service';
import { FiliereService } from './filiere.service';

@Component({
    selector: 'jhi-filiere-delete-dialog',
    templateUrl: './filiere-delete-dialog.component.html'
})
export class FiliereDeleteDialogComponent {

    filiere: Filiere;

    constructor(
        private filiereService: FiliereService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.filiereService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'filiereListModification',
                content: 'Deleted an filiere'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('mathFinEcoUniversityApp.filiere.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-filiere-delete-popup',
    template: ''
})
export class FiliereDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private filierePopupService: FilierePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.filierePopupService
                .open(FiliereDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
