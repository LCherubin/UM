import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Cours } from './cours.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CoursService {

    private resourceUrl = 'api/cours';
    private resourceSearchUrl = 'api/_search/cours';

    constructor(private http: Http) { }

    create(cours: Cours): Observable<Cours> {
        const copy = this.convert(cours);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(cours: Cours): Observable<Cours> {
        const copy = this.convert(cours);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Cours> {
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

    private convert(cours: Cours): Cours {
        const copy: Cours = Object.assign({}, cours);
        return copy;
    }
}
