import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Etudiant } from './etudiant.model';
import { EtudiantService } from './etudiant.service';

@Component({
    selector: 'jhi-etudiant-detail',
    templateUrl: './etudiant-detail.component.html'
})
export class EtudiantDetailComponent implements OnInit, OnDestroy {

    etudiant: Etudiant;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private etudiantService: EtudiantService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEtudiants();
    }

    load(id) {
        this.etudiantService.find(id).subscribe((etudiant) => {
            this.etudiant = etudiant;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEtudiants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'etudiantListModification',
            (response) => this.load(this.etudiant.id)
        );
    }
}
