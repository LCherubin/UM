import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { AnneeUniversitaire } from './annee-universitaire.model';
import { AnneeUniversitaireService } from './annee-universitaire.service';

@Component({
    selector: 'jhi-annee-universitaire-detail',
    templateUrl: './annee-universitaire-detail.component.html'
})
export class AnneeUniversitaireDetailComponent implements OnInit, OnDestroy {

    anneeUniversitaire: AnneeUniversitaire;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private anneeUniversitaireService: AnneeUniversitaireService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAnneeUniversitaires();
    }

    load(id) {
        this.anneeUniversitaireService.find(id).subscribe((anneeUniversitaire) => {
            this.anneeUniversitaire = anneeUniversitaire;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAnneeUniversitaires() {
        this.eventSubscriber = this.eventManager.subscribe(
            'anneeUniversitaireListModification',
            (response) => this.load(this.anneeUniversitaire.id)
        );
    }
}
