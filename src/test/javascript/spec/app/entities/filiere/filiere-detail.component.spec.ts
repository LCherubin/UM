import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { MathFinEcoUniversityTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FiliereDetailComponent } from '../../../../../../main/webapp/app/entities/filiere/filiere-detail.component';
import { FiliereService } from '../../../../../../main/webapp/app/entities/filiere/filiere.service';
import { Filiere } from '../../../../../../main/webapp/app/entities/filiere/filiere.model';

describe('Component Tests', () => {

    describe('Filiere Management Detail Component', () => {
        let comp: FiliereDetailComponent;
        let fixture: ComponentFixture<FiliereDetailComponent>;
        let service: FiliereService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MathFinEcoUniversityTestModule],
                declarations: [FiliereDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FiliereService,
                    EventManager
                ]
            }).overrideTemplate(FiliereDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FiliereDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FiliereService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Filiere(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.filiere).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
