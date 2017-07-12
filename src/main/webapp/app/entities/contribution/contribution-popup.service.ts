import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Contribution } from './contribution.model';
import { ContributionService } from './contribution.service';

@Injectable()
export class ContributionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private contributionService: ContributionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.contributionService.find(id).subscribe((contribution) => {
                if (contribution.datePayment) {
                    contribution.datePayment = {
                        year: contribution.datePayment.getFullYear(),
                        month: contribution.datePayment.getMonth() + 1,
                        day: contribution.datePayment.getDate()
                    };
                }
                this.contributionModalRef(component, contribution);
            });
        } else {
            return this.contributionModalRef(component, new Contribution());
        }
    }

    contributionModalRef(component: Component, contribution: Contribution): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.contribution = contribution;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
