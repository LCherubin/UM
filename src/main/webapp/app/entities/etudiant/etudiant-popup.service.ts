import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Etudiant } from './etudiant.model';
import { EtudiantService } from './etudiant.service';

@Injectable()
export class EtudiantPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private etudiantService: EtudiantService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.etudiantService.find(id).subscribe((etudiant) => {
                if (etudiant.dateNaissance) {
                    etudiant.dateNaissance = {
                        year: etudiant.dateNaissance.getFullYear(),
                        month: etudiant.dateNaissance.getMonth() + 1,
                        day: etudiant.dateNaissance.getDate()
                    };
                }
                if (etudiant.dateInscription) {
                    etudiant.dateInscription = {
                        year: etudiant.dateInscription.getFullYear(),
                        month: etudiant.dateInscription.getMonth() + 1,
                        day: etudiant.dateInscription.getDate()
                    };
                }
                this.etudiantModalRef(component, etudiant);
            });
        } else {
            return this.etudiantModalRef(component, new Etudiant());
        }
    }

    etudiantModalRef(component: Component, etudiant: Etudiant): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.etudiant = etudiant;
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
