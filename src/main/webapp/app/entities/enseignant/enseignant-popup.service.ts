import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Enseignant } from './enseignant.model';
import { EnseignantService } from './enseignant.service';

@Injectable()
export class EnseignantPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private enseignantService: EnseignantService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.enseignantService.find(id).subscribe((enseignant) => {
                if (enseignant.dateNaissance) {
                    enseignant.dateNaissance = {
                        year: enseignant.dateNaissance.getFullYear(),
                        month: enseignant.dateNaissance.getMonth() + 1,
                        day: enseignant.dateNaissance.getDate()
                    };
                }
                if (enseignant.dateEnregistrement) {
                    enseignant.dateEnregistrement = {
                        year: enseignant.dateEnregistrement.getFullYear(),
                        month: enseignant.dateEnregistrement.getMonth() + 1,
                        day: enseignant.dateEnregistrement.getDate()
                    };
                }
                this.enseignantModalRef(component, enseignant);
            });
        } else {
            return this.enseignantModalRef(component, new Enseignant());
        }
    }

    enseignantModalRef(component: Component, enseignant: Enseignant): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.enseignant = enseignant;
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
