import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Parent } from './parent.model';
import { ParentPopupService } from './parent-popup.service';
import { ParentService } from './parent.service';

@Component({
    selector: 'jhi-parent-delete-dialog',
    templateUrl: './parent-delete-dialog.component.html'
})
export class ParentDeleteDialogComponent {

    parent: Parent;

    constructor(
        private parentService: ParentService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.parentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'parentListModification',
                content: 'Deleted an parent'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('mathFinEcoUniversityApp.parent.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-parent-delete-popup',
    template: ''
})
export class ParentDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parentPopupService: ParentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.parentPopupService
                .open(ParentDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
