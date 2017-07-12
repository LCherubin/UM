import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { MathFinEcoUniversityTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CoursDetailComponent } from '../../../../../../main/webapp/app/entities/cours/cours-detail.component';
import { CoursService } from '../../../../../../main/webapp/app/entities/cours/cours.service';
import { Cours } from '../../../../../../main/webapp/app/entities/cours/cours.model';

describe('Component Tests', () => {

    describe('Cours Management Detail Component', () => {
        let comp: CoursDetailComponent;
        let fixture: ComponentFixture<CoursDetailComponent>;
        let service: CoursService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MathFinEcoUniversityTestModule],
                declarations: [CoursDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CoursService,
                    EventManager
                ]
            }).overrideTemplate(CoursDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoursDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoursService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Cours(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cours).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
