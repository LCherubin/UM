import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Filiere } from './filiere.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FiliereService {

    private resourceUrl = 'api/filieres';
    private resourceSearchUrl = 'api/_search/filieres';

    constructor(private http: Http) { }

    create(filiere: Filiere): Observable<Filiere> {
        const copy = this.convert(filiere);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(filiere: Filiere): Observable<Filiere> {
        const copy = this.convert(filiere);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Filiere> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(filiere: Filiere): Filiere {
        const copy: Filiere = Object.assign({}, filiere);
        return copy;
    }
}
