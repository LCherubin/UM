import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Parent } from './parent.model';
import { ParentService } from './parent.service';

@Component({
    selector: 'jhi-parent-detail',
    templateUrl: './parent-detail.component.html'
})
export class ParentDetailComponent implements OnInit, OnDestroy {

    parent: Parent;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private parentService: ParentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInParents();
    }

    load(id) {
        this.parentService.find(id).subscribe((parent) => {
            this.parent = parent;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInParents() {
        this.eventSubscriber = this.eventManager.subscribe(
            'parentListModification',
            (response) => this.load(this.parent.id)
        );
    }
}
