import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { MathFinEcoUniversityTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EnseignantDetailComponent } from '../../../../../../main/webapp/app/entities/enseignant/enseignant-detail.component';
import { EnseignantService } from '../../../../../../main/webapp/app/entities/enseignant/enseignant.service';
import { Enseignant } from '../../../../../../main/webapp/app/entities/enseignant/enseignant.model';

describe('Component Tests', () => {

    describe('Enseignant Management Detail Component', () => {
        let comp: EnseignantDetailComponent;
        let fixture: ComponentFixture<EnseignantDetailComponent>;
        let service: EnseignantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MathFinEcoUniversityTestModule],
                declarations: [EnseignantDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EnseignantService,
                    EventManager
                ]
            }).overrideTemplate(EnseignantDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EnseignantDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnseignantService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Enseignant(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.enseignant).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
