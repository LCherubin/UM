import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { MathFinEcoUniversityTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EtudiantDetailComponent } from '../../../../../../main/webapp/app/entities/etudiant/etudiant-detail.component';
import { EtudiantService } from '../../../../../../main/webapp/app/entities/etudiant/etudiant.service';
import { Etudiant } from '../../../../../../main/webapp/app/entities/etudiant/etudiant.model';

describe('Component Tests', () => {

    describe('Etudiant Management Detail Component', () => {
        let comp: EtudiantDetailComponent;
        let fixture: ComponentFixture<EtudiantDetailComponent>;
        let service: EtudiantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MathFinEcoUniversityTestModule],
                declarations: [EtudiantDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EtudiantService,
                    EventManager
                ]
            }).overrideTemplate(EtudiantDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EtudiantDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EtudiantService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Etudiant(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.etudiant).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
