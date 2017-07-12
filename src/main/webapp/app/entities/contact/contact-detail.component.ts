import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Contact } from './contact.model';
import { ContactService } from './contact.service';

@Component({
    selector: 'jhi-contact-detail',
    templateUrl: './contact-detail.component.html'
})
export class ContactDetailComponent implements OnInit, OnDestroy {

    contact: Contact;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private contactService: ContactService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInContacts();
        this.loadLast();
    }

    load(id) {
        this.contactService.find(id).subscribe((contact) => {
            this.contact = contact;
        });
    }

    loadLast() {
        this.contactService.last().subscribe((contact) => {
            this.contact = contact;
            console.log(contact);

        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInContacts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'contactListModification',
            (response) => this.load(this.contact.id)
        );
    }
}
