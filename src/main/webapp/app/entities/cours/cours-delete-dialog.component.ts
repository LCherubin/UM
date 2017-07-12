import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Cours } from './cours.model';
import { CoursPopupService } from './cours-popup.service';
import { CoursService } from './cours.service';

@Component({
    selector: 'jhi-cours-delete-dialog',
    templateUrl: './cours-delete-dialog.component.html'
})
export class CoursDeleteDialogComponent {

    cours: Cours;

    constructor(
        private coursService: CoursService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.coursService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'coursListModification',
                content: 'Deleted an cours'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('mathFinEcoUniversityApp.cours.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-cours-delete-popup',
    template: ''
})
export class CoursDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private coursPopupService: CoursPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.coursPopupService
                .open(CoursDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
