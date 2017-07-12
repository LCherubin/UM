import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Enseignant } from './enseignant.model';
import { EnseignantService } from './enseignant.service';

@Component({
    selector: 'jhi-enseignant-detail',
    templateUrl: './enseignant-detail.component.html'
})
export class EnseignantDetailComponent implements OnInit, OnDestroy {

    enseignant: Enseignant;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private enseignantService: EnseignantService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEnseignants();
    }

    load(id) {
        this.enseignantService.find(id).subscribe((enseignant) => {
            this.enseignant = enseignant;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEnseignants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'enseignantListModification',
            (response) => this.load(this.enseignant.id)
        );
    }
}
