import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Parent } from './parent.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ParentService {

    private resourceUrl = 'api/parents';
    private resourceSearchUrl = 'api/_search/parents';

    constructor(private http: Http) { }

    create(parent: Parent): Observable<Parent> {
        const copy = this.convert(parent);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(parent: Parent): Observable<Parent> {
        const copy = this.convert(parent);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Parent> {
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

    private convert(parent: Parent): Parent {
        const copy: Parent = Object.assign({}, parent);
        return copy;
    }
}
