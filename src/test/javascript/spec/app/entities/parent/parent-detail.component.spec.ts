import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { MathFinEcoUniversityTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ParentDetailComponent } from '../../../../../../main/webapp/app/entities/parent/parent-detail.component';
import { ParentService } from '../../../../../../main/webapp/app/entities/parent/parent.service';
import { Parent } from '../../../../../../main/webapp/app/entities/parent/parent.model';

describe('Component Tests', () => {

    describe('Parent Management Detail Component', () => {
        let comp: ParentDetailComponent;
        let fixture: ComponentFixture<ParentDetailComponent>;
        let service: ParentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MathFinEcoUniversityTestModule],
                declarations: [ParentDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ParentService,
                    EventManager
                ]
            }).overrideTemplate(ParentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParentService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Parent(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.parent).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
