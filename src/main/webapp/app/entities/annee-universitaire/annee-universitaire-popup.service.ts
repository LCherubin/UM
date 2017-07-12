import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AnneeUniversitaire } from './annee-universitaire.model';
import { AnneeUniversitaireService } from './annee-universitaire.service';

@Injectable()
export class AnneeUniversitairePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private anneeUniversitaireService: AnneeUniversitaireService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.anneeUniversitaireService.find(id).subscribe((anneeUniversitaire) => {
                this.anneeUniversitaireModalRef(component, anneeUniversitaire);
            });
        } else {
            return this.anneeUniversitaireModalRef(component, new AnneeUniversitaire());
        }
    }

    anneeUniversitaireModalRef(component: Component, anneeUniversitaire: AnneeUniversitaire): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.anneeUniversitaire = anneeUniversitaire;
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
