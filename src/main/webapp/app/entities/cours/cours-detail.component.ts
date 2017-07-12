import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Cours } from './cours.model';
import { CoursService } from './cours.service';

@Component({
    selector: 'jhi-cours-detail',
    templateUrl: './cours-detail.component.html'
})
export class CoursDetailComponent implements OnInit, OnDestroy {

    cours: Cours;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private coursService: CoursService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCours();
    }

    load(id) {
        this.coursService.find(id).subscribe((cours) => {
            this.cours = cours;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCours() {
        this.eventSubscriber = this.eventManager.subscribe(
            'coursListModification',
            (response) => this.load(this.cours.id)
        );
    }
}
