import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Contribution } from './contribution.model';
import { ContributionPopupService } from './contribution-popup.service';
import { ContributionService } from './contribution.service';

@Component({
    selector: 'jhi-contribution-delete-dialog',
    templateUrl: './contribution-delete-dialog.component.html'
})
export class ContributionDeleteDialogComponent {

    contribution: Contribution;

    constructor(
        private contributionService: ContributionService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contributionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'contributionListModification',
                content: 'Deleted an contribution'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('mathFinEcoUniversityApp.contribution.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-contribution-delete-popup',
    template: ''
})
export class ContributionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contributionPopupService: ContributionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.contributionPopupService
                .open(ContributionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
