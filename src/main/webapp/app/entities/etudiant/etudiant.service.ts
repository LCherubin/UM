import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { Etudiant } from './etudiant.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EtudiantService {

    private resourceUrl = 'api/etudiants';
    private resourceSearchUrl = 'api/_search/etudiants';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(etudiant: Etudiant): Observable<Etudiant> {
        const copy = this.convert(etudiant);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(etudiant: Etudiant): Observable<Etudiant> {
        const copy = this.convert(etudiant);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Etudiant> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.dateNaissance = this.dateUtils
            .convertLocalDateFromServer(entity.dateNaissance);
        entity.dateInscription = this.dateUtils
            .convertLocalDateFromServer(entity.dateInscription);
    }

    private convert(etudiant: Etudiant): Etudiant {
        const copy: Etudiant = Object.assign({}, etudiant);
        copy.dateNaissance = this.dateUtils
            .convertLocalDateToServer(etudiant.dateNaissance);
        copy.dateInscription = this.dateUtils
            .convertLocalDateToServer(etudiant.dateInscription);
        return copy;
    }
}
