import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { MathFinEcoUniversityTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AnneeUniversitaireDetailComponent } from '../../../../../../main/webapp/app/entities/annee-universitaire/annee-universitaire-detail.component';
import { AnneeUniversitaireService } from '../../../../../../main/webapp/app/entities/annee-universitaire/annee-universitaire.service';
import { AnneeUniversitaire } from '../../../../../../main/webapp/app/entities/annee-universitaire/annee-universitaire.model';

describe('Component Tests', () => {

    describe('AnneeUniversitaire Management Detail Component', () => {
        let comp: AnneeUniversitaireDetailComponent;
        let fixture: ComponentFixture<AnneeUniversitaireDetailComponent>;
        let service: AnneeUniversitaireService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MathFinEcoUniversityTestModule],
                declarations: [AnneeUniversitaireDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AnneeUniversitaireService,
                    EventManager
                ]
            }).overrideTemplate(AnneeUniversitaireDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnneeUniversitaireDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnneeUniversitaireService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AnneeUniversitaire(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.anneeUniversitaire).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
