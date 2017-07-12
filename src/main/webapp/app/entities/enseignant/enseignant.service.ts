import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { Enseignant } from './enseignant.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EnseignantService {

    private resourceUrl = 'api/enseignants';
    private resourceSearchUrl = 'api/_search/enseignants';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(enseignant: Enseignant): Observable<Enseignant> {
        const copy = this.convert(enseignant);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(enseignant: Enseignant): Observable<Enseignant> {
        const copy = this.convert(enseignant);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Enseignant> {
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
        entity.dateEnregistrement = this.dateUtils
            .convertLocalDateFromServer(entity.dateEnregistrement);
    }

    private convert(enseignant: Enseignant): Enseignant {
        const copy: Enseignant = Object.assign({}, enseignant);
        copy.dateNaissance = this.dateUtils
            .convertLocalDateToServer(enseignant.dateNaissance);
        copy.dateEnregistrement = this.dateUtils
            .convertLocalDateToServer(enseignant.dateEnregistrement);
        return copy;
    }
}
