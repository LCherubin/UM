import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Filiere } from './filiere.model';
import { FiliereService } from './filiere.service';

@Component({
    selector: 'jhi-filiere-detail',
    templateUrl: './filiere-detail.component.html'
})
export class FiliereDetailComponent implements OnInit, OnDestroy {

    filiere: Filiere;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private filiereService: FiliereService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFilieres();
    }

    load(id) {
        this.filiereService.find(id).subscribe((filiere) => {
            this.filiere = filiere;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFilieres() {
        this.eventSubscriber = this.eventManager.subscribe(
            'filiereListModification',
            (response) => this.load(this.filiere.id)
        );
    }
}
