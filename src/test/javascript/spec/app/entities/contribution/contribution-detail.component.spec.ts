import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { MathFinEcoUniversityTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ContributionDetailComponent } from '../../../../../../main/webapp/app/entities/contribution/contribution-detail.component';
import { ContributionService } from '../../../../../../main/webapp/app/entities/contribution/contribution.service';
import { Contribution } from '../../../../../../main/webapp/app/entities/contribution/contribution.model';

describe('Component Tests', () => {

    describe('Contribution Management Detail Component', () => {
        let comp: ContributionDetailComponent;
        let fixture: ComponentFixture<ContributionDetailComponent>;
        let service: ContributionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MathFinEcoUniversityTestModule],
                declarations: [ContributionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ContributionService,
                    EventManager
                ]
            }).overrideTemplate(ContributionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContributionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContributionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Contribution(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.contribution).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
